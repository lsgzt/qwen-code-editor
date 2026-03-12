package com.pocketdev.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pocketdev.PocketDevApplication
import com.pocketdev.data.local.SettingsManager
import com.pocketdev.data.remote.groq.GroqRepository
import com.pocketdev.domain.model.*
import com.pocketdev.util.ExecutionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Main ViewModel for the app
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = PocketDevApplication.database
    private val projectDao = db.projectDao()
    private val settingsManager = PocketDevApplication.settingsManager
    
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _currentProject = MutableStateFlow<Project?>(null)
    val currentProject: StateFlow<Project?> = _currentProject.asStateFlow()

    private val _executionResult = MutableStateFlow<ExecutionResult?>(null)
    val executionResult: StateFlow<ExecutionResult?> = _executionResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _aiResponse = MutableStateFlow<AiResponse?>(null)
    val aiResponse: StateFlow<AiResponse?> = _aiResponse.asStateFlow()

    val settings: StateFlow<AppSettings> = settingsManager.getSettings().let { MutableStateFlow(it) }

    private var executionManager: ExecutionManager? = null
    private var groqRepository: GroqRepository? = null

    init {
        loadProjects()
    }

    fun initializeExecutionManager() {
        executionManager = ExecutionManager(getApplication())
    }

    private fun loadProjects() {
        viewModelScope.launch {
            projectDao.getAllProjects().collect { projects ->
                _projects.value = projects
            }
        }
    }

    fun createNewProject(name: String, language: Language) {
        viewModelScope.launch {
            val project = Project(
                name = name,
                language = language,
                code = getDefaultCodeForLanguage(language)
            )
            val id = projectDao.insertProject(project)
            _currentProject.value = project.copy(id = id)
        }
    }

    fun openProject(project: Project) {
        _currentProject.value = project
    }

    fun updateCurrentCode(code: String) {
        _currentProject.value?.let { project ->
            val updated = project.copy(
                code = code,
                modifiedAt = System.currentTimeMillis()
            )
            _currentProject.value = updated
            viewModelScope.launch {
                projectDao.updateProject(updated)
            }
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            projectDao.deleteProject(project)
            if (_currentProject.value?.id == project.id) {
                _currentProject.value = null
            }
        }
    }

    fun runCode() {
        val project = _currentProject.value ?: return
        val manager = executionManager ?: run {
            _errorMessage.value = "Execution engine not initialized"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val result = manager.execute(project.code, project.language.displayName)
                _executionResult.value = result
            } catch (e: Exception) {
                _executionResult.value = ExecutionResult.failure(e.message ?: "Unknown error", 0)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearOutput() {
        _executionResult.value = null
    }

    // AI Features
    fun fixBug() {
        val project = _currentProject.value ?: return
        callGroqApi { repo -> repo.fixBug(project.code, project.language.displayName) }
    }

    fun explainCode() {
        val project = _currentProject.value ?: return
        callGroqApi { repo -> repo.explainCode(project.code, project.language.displayName) }
    }

    fun improveCode() {
        val project = _currentProject.value ?: return
        callGroqApi { repo -> repo.improveCode(project.code, project.language.displayName) }
    }

    private fun callGroqApi(call: suspend (GroqRepository) -> Result<AiResponse>) {
        val apiKey = settingsManager.getGroqApiKey()
        if (apiKey.isBlank()) {
            _errorMessage.value = "Please set your Groq API key in Settings first"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                groqRepository = GroqRepository(apiKey)
                val result = call(groqRepository!!)
                result.fold(
                    onSuccess = { response ->
                        _aiResponse.value = response
                    },
                    onFailure = { error ->
                        _errorMessage.value = "AI Error: ${error.message}"
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "AI Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun applyAiFix(newCode: String) {
        updateCurrentCode(newCode)
        _aiResponse.value = null
    }

    fun dismissAiResponse() {
        _aiResponse.value = null
    }

    fun saveSettings(newSettings: AppSettings) {
        viewModelScope.launch {
            settingsManager.saveSettings(newSettings)
            // Settings would trigger theme update in real implementation
        }
    }

    fun searchProjects(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                loadProjects()
            } else {
                projectDao.searchProjects(query).collect { projects ->
                    _projects.value = projects
                }
            }
        }
    }

    private fun getDefaultCodeForLanguage(language: Language): String {
        return when (language) {
            Language.PYTHON -> "# Start coding in Python!\nprint(\"Hello, World!\")"
            Language.JAVASCRIPT -> "// Start coding in JavaScript!\nconsole.log(\"Hello, World!\");"
            Language.HTML -> "<!-- Start coding in HTML! -->\n<!DOCTYPE html>\n<html>\n<head>\n    <title>My Page</title>\n</head>\n<body>\n    <h1>Hello, World!</h1>\n</body>\n</html>"
            Language.CSS -> "/* Start styling with CSS! */\nbody {\n    font-family: Arial, sans-serif;\n    margin: 20px;\n}"
            Language.JAVA -> "// Start coding in Java!\npublic class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}"
            Language.CPP -> "// Start coding in C++!\n#include <iostream>\n\nint main() {\n    std::cout << \"Hello, World!\" << std::endl;\n    return 0;\n}"
            Language.KOTLIN -> "// Start coding in Kotlin!\nfun main() {\n    println(\"Hello, World!\")\n}"
            Language.JSON -> "{\n  \"key\": \"value\"\n}"
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
