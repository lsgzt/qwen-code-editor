package com.pocketdev.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a code project in the PocketDev app
 */
@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val language: Language,
    val code: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

/**
 * Supported programming languages
 */
enum class Language(val displayName: String, val extension: String, val highlightLanguage: String) {
    PYTHON("Python", "py", "python"),
    JAVASCRIPT("JavaScript", "js", "javascript"),
    HTML("HTML", "html", "html"),
    CSS("CSS", "css", "css"),
    JAVA("Java", "java", "java"),
    CPP("C++", "cpp", "cpp"),
    KOTLIN("Kotlin", "kt", "kotlin"),
    JSON("JSON", "json", "json");

    companion object {
        fun fromDisplayName(name: String): Language {
            return values().find { it.displayName == name } ?: PYTHON
        }

        fun fromExtension(ext: String): Language {
            return values().find { it.extension == ext.lowercase() } ?: PYTHON
        }
    }
}

/**
 * Execution result from code runner
 */
data class ExecutionResult(
    val output: String,
    val error: String? = null,
    val executionTime: Long,
    val success: Boolean
) {
    companion object {
        fun success(output: String, time: Long) = ExecutionResult(output, null, time, true)
        fun failure(error: String, time: Long) = ExecutionResult("", error, time, false)
    }
}

/**
 * Code example for learning
 */
data class CodeExample(
    val id: String,
    val title: String,
    val description: String,
    val language: Language,
    val code: String,
    val category: ExampleCategory,
    val difficulty: DifficultyLevel = DifficultyLevel.BEGINNER
)

enum class ExampleCategory {
    BASICS,
    CONTROL_FLOW,
    FUNCTIONS,
    DATA_STRUCTURES,
    OOP,
    WEB,
    ADVANCED
}

enum class DifficultyLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

/**
 * AI Assistant response
 */
data class AiResponse(
    val content: String,
    val originalCode: String,
    val suggestedCode: String? = null,
    val explanation: String? = null,
    val improvements: List<String> = emptyList()
)

/**
 * Settings for the app
 */
data class AppSettings(
    val groqApiKey: String = "",
    val theme: Theme = Theme.DARK,
    val fontSize: Float = 14f,
    val tabSize: Int = 4,
    val autoSaveEnabled: Boolean = true,
    val autocompleteEnabled: Boolean = true,
    val lineNumbersEnabled: Boolean = true,
    val autoSaveIntervalSeconds: Int = 30
)

enum class Theme {
    DARK,
    LIGHT,
    SYSTEM
}
