package com.pocketdev.util

import android.content.Context
import android.widget.Toast
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.pocketdev.domain.model.ExecutionResult
import org.mozilla.javascript.Context
import org.mozilla.javascript.JavaScriptException
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * Python execution engine using Chaquopy
 */
class PythonEngine {

    companion object {
        private var pythonInitialized = false

        fun initialize(context: Context) {
            if (!pythonInitialized) {
                if (!Python.isStarted()) {
                    Python.start(AndroidPlatform(context))
                }
                pythonInitialized = true
            }
        }

        /**
         * Execute Python code and return result
         */
        fun execute(code: String, timeoutMs: Long = 10000): ExecutionResult {
            val startTime = System.currentTimeMillis()
            
            try {
                // Redirect stdout and stderr
                val outputStream = ByteArrayOutputStream()
                val errorStream = ByteArrayOutputStream()
                val originalOut = System.out
                val originalErr = System.err
                
                try {
                    System.setOut(PrintStream(outputStream))
                    System.setErr(PrintStream(errorStream))

                    val py = Python.getInstance()
                    val module = py.getModule("builtins")
                    
                    // Execute the code
                    py.getModule("io")
                    val execCode = """
                        |import sys
                        |from io import StringIO
                        |old_stdout = sys.stdout
                        |old_stderr = sys.stderr
                        |sys.stdout = StringIO()
                        |sys.stderr = StringIO()
                        |try:
                        |    exec('''$code''')
                        |except Exception as e:
                        |    print(f"Error: {e}", file=sys.stderr)
                        |output = sys.stdout.getvalue()
                        |error = sys.stderr.getvalue()
                        |sys.stdout = old_stdout
                        |sys.stderr = old_stderr
                        |output, error
                    """.trimMargin()

                    val result = py.callAttr("exec", execCode)
                    val output = outputStream.toString().trim()
                    val error = errorStream.toString().trim()

                    val executionTime = System.currentTimeMillis() - startTime

                    return when {
                        error.isNotEmpty() && !error.contains("Error:") -> 
                            ExecutionResult.failure(error, executionTime)
                        output.isNotEmpty() -> 
                            ExecutionResult.success(output, executionTime)
                        else -> 
                            ExecutionResult.success("Code executed successfully (no output)", executionTime)
                    }
                } finally {
                    System.setOut(originalOut)
                    System.setErr(originalErr)
                }
            } catch (e: Exception) {
                val executionTime = System.currentTimeMillis() - startTime
                return ExecutionResult.failure("Python Error: ${e.message ?: "Unknown error"}", executionTime)
            }
        }
    }
}

/**
 * JavaScript execution engine using Rhino
 */
class JavaScriptEngine {

    /**
     * Execute JavaScript code and return result
     */
    fun execute(code: String, timeoutMs: Long = 10000): ExecutionResult {
        val startTime = System.currentTimeMillis()
        
        return try {
            val cx = Context.enter()
            cx.optimizationLevel = -1 // Enable interpreted mode for safety
            
            try {
                cx.languageVersion = Context.VERSION_ES6
                
                val scope = cx.initStandardObjects()
                
                // Capture console.log output
                val consoleObject = cx.newObject(scope)
                val logs = mutableListOf<String>()
                
                consoleObject.put("log", consoleObject, object : org.mozilla.javascript.BaseFunction() {
                    override fun call(cx: Context, scope: org.mozilla.javascript.Scriptable, 
                                    thisObj: org.mozilla.javascript.Scriptable?, args: Array<Any?>): Any {
                        val logString = args.joinToString(" ") { it?.toString() ?: "undefined" }
                        logs.add(logString)
                        return org.mozilla.javascript.Undefined.INSTANCE
                    }
                })
                
                scope.put("console", scope, consoleObject)
                
                // Execute with timeout protection
                cx.executeScriptWithTimeout({
                    cx.evaluateString(scope, code, "UserCode", 1, null)
                }, timeoutMs)
                
                val executionTime = System.currentTimeMillis() - startTime
                val output = logs.joinToString("\n")
                
                if (output.isNotEmpty()) {
                    ExecutionResult.success(output, executionTime)
                } else {
                    ExecutionResult.success("Code executed successfully (no output)", executionTime)
                }
            } catch (e: JavaScriptException) {
                val executionTime = System.currentTimeMillis() - startTime
                ExecutionResult.failure("JavaScript Error: ${e.message}\nLine: ${e.lineNumber()}", executionTime)
            } catch (e: Exception) {
                val executionTime = System.currentTimeMillis() - startTime
                ExecutionResult.failure("JavaScript Error: ${e.message ?: "Unknown error"}", executionTime)
            } finally {
                Context.exit()
            }
        } catch (e: Exception) {
            val executionTime = System.currentTimeMillis() - startTime
            ExecutionResult.failure("Execution Error: ${e.message ?: "Unknown error"}", executionTime)
        }
    }

    /**
     * Helper to execute with timeout
     */
    private fun Context.executeScriptWithTimeout(block: () -> Unit, timeoutMs: Long) {
        // Simple timeout implementation using thread
        val thread = Thread(block)
        thread.start()
        thread.join(timeoutMs)
        
        if (thread.isAlive) {
            thread.interrupt()
            throw RuntimeException("Execution timeout after ${timeoutMs}ms")
        }
    }
}

/**
 * HTML rendering engine using Android WebView
 */
class HtmlEngine {

    /**
     * Render HTML content
     * Returns the HTML to be rendered in WebView
     */
    fun prepareHtml(code: String): String {
        // If the code already contains a full HTML document, use it as-is
        return if (code.trim().startsWith("<!DOCTYPE html>") || code.trim().startsWith("<html")) {
            code
        } else {
            // Wrap in basic HTML structure
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    * { box-sizing: border-box; }
                </style>
            </head>
            <body>
                $code
            </body>
            </html>
            """.trimIndent()
        }
    }

    /**
     * Extract JavaScript errors from console
     */
    fun parseJsError(errorMessage: String, sourceId: String, lineNumber: Int): String {
        return "JavaScript Error in HTML:\n$errorMessage\nSource: $sourceId\nLine: $lineNumber"
    }
}

/**
 * Unified execution manager that routes to appropriate engine
 */
class ExecutionManager(private val context: Context) {

    private val pythonEngine = PythonEngine
    private val jsEngine = JavaScriptEngine()
    private val htmlEngine = HtmlEngine()

    init {
        pythonEngine.initialize(context)
    }

    /**
     * Execute code based on language type
     */
    fun execute(code: String, language: String): ExecutionResult {
        return when (language.uppercase()) {
            "PYTHON" -> pythonEngine.execute(code)
            "JAVASCRIPT" -> jsEngine.execute(code)
            "HTML" -> {
                // HTML doesn't produce console output directly, but we can validate it
                val html = htmlEngine.prepareHtml(code)
                ExecutionResult.success("HTML ready for rendering. Tap 'View Output' to preview.", 0)
            }
            else -> ExecutionResult.failure("Execution not supported for this language", 0)
        }
    }

    /**
     * Check if language is executable
     */
    fun isExecutable(language: String): Boolean {
        return language.uppercase() in listOf("PYTHON", "JAVASCRIPT", "HTML")
    }
}
