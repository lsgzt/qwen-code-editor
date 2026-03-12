package com.pocketdev.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketdev.domain.model.AiResponse
import com.pocketdev.domain.model.ExecutionResult
import com.pocketdev.domain.model.Language
import com.pocketdev.domain.model.Project

/**
 * Language selector dropdown
 */
@Composable
fun LanguageSelector(
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedLanguage.displayName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Language") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Language.values().forEach { language ->
                DropdownMenuItem(
                    text = { Text(language.displayName) },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * AI Action Buttons Bar
 */
@Composable
fun AiActionBar(
    onFixBug: () -> Unit,
    onExplainCode: () -> Unit,
    onImproveCode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onFixBug, modifier = Modifier.weight(1f)) {
            Icon(
                androidx.compose.material.icons.Icons.Default.BugReport,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("Fix Bug", fontSize = 12.sp)
        }
        
        Spacer(Modifier.width(8.dp))
        
        Button(onClick = onExplainCode, modifier = Modifier.weight(1f)) {
            Icon(
                androidx.compose.material.icons.Icons.Default.Lightbulb,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("Explain", fontSize = 12.sp)
        }
        
        Spacer(Modifier.width(8.dp))
        
        Button(onClick = onImproveCode, modifier = Modifier.weight(1f)) {
            Icon(
                androidx.compose.material.icons.Icons.Default.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("Improve", fontSize = 12.sp)
        }
    }
}

/**
 * Code Editor Placeholder (to be replaced with Sora Editor)
 */
@Composable
fun CodeEditorPlaceholder(
    code: String,
    language: Language,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = code,
        onValueChange = onCodeChange,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        textStyle = LocalTextStyle.current.copy(
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF1E1E1E),
            unfocusedContainerColor = Color(0xFF1E1E1E),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        maxLines = Int.MAX_VALUE
    )
}

/**
 * Console Output Display
 */
@Composable
fun ConsoleOutput(
    result: ExecutionResult?,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Console Output",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            
            TextButton(onClick = onClear) {
                Text("Clear")
            }
        }
        
        Divider()
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (result == null) {
                Text(
                    "Run your code to see output here...",
                    color = Color.Gray,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            } else {
                Column {
                    if (result.success) {
                        Text(
                            "✓ Execution Successful",
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            "✗ Execution Failed",
                            color = Color(0xFFF44336),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    if (result.executionTime > 0) {
                        Text(
                            "Time: ${result.executionTime}ms",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Spacer(Modifier.height(8.dp))
                    
                    Text(
                        text = if (result.error != null) result.error else result.output,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = if (result.error != null) Color(0xFFFF5252) else Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
}

/**
 * Projects List
 */
@Composable
fun ProjectsList(
    projects: List<Project>,
    onProjectClick: (Project) -> Unit,
    onDeleteClick: (Project) -> Unit,
    onCreateNew: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "My Projects",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            
            Button(onClick = onCreateNew) {
                Icon(
                    androidx.compose.material.icons.Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text("New")
            }
        }
        
        if (projects.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No projects yet. Create your first project!",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(projects.size) { index ->
                    val project = projects[index]
                    ProjectCard(
                        project = project,
                        onClick = { onProjectClick(project) },
                        onDelete = { onDeleteClick(project) }
                    )
                }
            }
        }
    }
}

/**
 * Individual Project Card
 */
@Composable
fun ProjectCard(
    project: Project,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    project.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                
                Spacer(Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge {
                        Text(project.language.displayName)
                    }
                    
                    Spacer(Modifier.width(8.dp))
                    
                    Text(
                        "Modified: ${java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(project.modifiedAt))}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    androidx.compose.material.icons.Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

/**
 * Settings Panel
 */
@Composable
fun SettingsPanel(
    settings: com.pocketdev.domain.model.AppSettings,
    onSaveSettings: (com.pocketdev.domain.model.AppSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    var apiKey by remember { mutableStateOf(settings.groqApiKey) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Settings",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Groq API Key",
                    fontWeight = FontWeight.Medium
                )
                
                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("API Key") },
                    placeholder = { Text("Enter your Groq API key") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Text(
                    "Get your API key from https://console.groq.com",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                
                Button(
                    onClick = {
                        onSaveSettings(settings.copy(groqApiKey = apiKey))
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save API Key")
                }
            }
        }
        
        // Additional settings can be added here
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Editor Settings",
                    fontWeight = FontWeight.Medium
                )
                
                // Theme selection, font size, etc. would go here
                Text("More settings coming soon...")
            }
        }
    }
}

/**
 * Loading Overlay
 */
@Composable
fun LoadingOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * AI Response Dialog
 */
@Composable
fun AiResponseDialog(
    response: AiResponse,
    onApply: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("AI Assistant") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(response.content)
                
                response.suggestedCode?.let { code ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1E1E1E)
                        )
                    ) {
                        Text(
                            text = code,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (response.suggestedCode != null) {
                Button(onClick = { onApply(response.suggestedCode!!) }) {
                    Text("Apply Fix")
                }
            } else {
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
