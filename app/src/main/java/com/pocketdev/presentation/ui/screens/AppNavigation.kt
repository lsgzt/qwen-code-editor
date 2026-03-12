package com.pocketdev.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pocketdev.domain.model.Language
import com.pocketdev.presentation.viewmodel.MainViewModel
import com.pocketdev.presentation.ui.components.*

/**
 * Main app navigation and screen composition
 */
@Composable
fun AppNavigation(viewModel: MainViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val currentProject by viewModel.currentProject.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    
    // Initialize execution manager on first launch
    LaunchedEffect(Unit) {
        viewModel.initializeExecutionManager()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PocketDev") },
                actions = {
                    // Language selector
                    LanguageSelector(
                        selectedLanguage = currentProject?.language ?: Language.PYTHON,
                        onLanguageSelected = { /* Handle language change */ }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(androidx.compose.material.icons.Icons.Default.Code, contentDescription = "Editor") },
                    label = { Text("Editor") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(androidx.compose.material.icons.Icons.Default.Terminal, contentDescription = "Console") },
                    label = { Text("Console") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(androidx.compose.material.icons.Icons.Default.Folder, contentDescription = "Projects") },
                    label = { Text("Projects") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(androidx.compose.material.icons.Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
            }
        },
        floatingActionButton = {
            if (selectedTab == 0) { // Editor tab
                FloatingActionButton(
                    onClick = { viewModel.runCode() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.PlayArrow,
                        contentDescription = "Run Code"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> EditorScreen(viewModel = viewModel)
                1 -> ConsoleScreen(viewModel = viewModel)
                2 -> ProjectsScreen(viewModel = viewModel)
                3 -> SettingsScreen(viewModel = viewModel)
            }
            
            // Loading indicator
            if (isLoading) {
                LoadingOverlay()
            }
            
            // Error snackbar
            errorMessage?.let { error ->
                LaunchedEffect(error) {
                    // Show error and clear after delay
                    kotlinx.coroutines.delay(5000)
                    viewModel.clearError()
                }
            }
        }
    }
}

@Composable
fun EditorScreen(viewModel: MainViewModel) {
    val currentProject by viewModel.currentProject.collectAsStateWithLifecycle()
    val aiResponse by viewModel.aiResponse.collectAsStateWithLifecycle()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // AI Action Buttons
        AiActionBar(
            onFixBug = { viewModel.fixBug() },
            onExplainCode = { viewModel.explainCode() },
            onImproveCode = { viewModel.improveCode() }
        )
        
        // Code Editor (placeholder - will use Sora Editor in full implementation)
        CodeEditorPlaceholder(
            code = currentProject?.code ?: "",
            language = currentProject?.language ?: Language.PYTHON,
            onCodeChange = { viewModel.updateCurrentCode(it) }
        )
    }
    
    // AI Response Dialog
    aiResponse?.let { response ->
        AiResponseDialog(
            response = response,
            onApply = { viewModel.applyAiFix(it) },
            onDismiss = { viewModel.dismissAiResponse() }
        )
    }
}

@Composable
fun ConsoleScreen(viewModel: MainViewModel) {
    val executionResult by viewModel.executionResult.collectAsStateWithLifecycle()
    
    ConsoleOutput(
        result = executionResult,
        onClear = { viewModel.clearOutput() }
    )
}

@Composable
fun ProjectsScreen(viewModel: MainViewModel) {
    val projects by viewModel.projects.collectAsStateWithLifecycle()
    
    ProjectsList(
        projects = projects,
        onProjectClick = { viewModel.openProject(it) },
        onDeleteClick = { viewModel.deleteProject(it) },
        onCreateNew = { /* Navigate to create new project */ }
    )
}

@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    
    SettingsPanel(
        settings = settings,
        onSaveSettings = { viewModel.saveSettings(it) }
    )
}
