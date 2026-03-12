package com.pocketdev.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pocketdev.presentation.viewmodel.MainViewModel
import com.pocketdev.presentation.ui.screens.AppNavigation
import com.pocketdev.ui.theme.PocketDevTheme

/**
 * Main Activity - Entry point for the app
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            PocketDevTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainViewModel = viewModel {
                        MainViewModel(applicationContext)
                    }
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
