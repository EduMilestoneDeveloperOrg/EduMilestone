package com.edumilestone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.edumilestone.app.ui.HomeScreen
import com.edumilestone.app.ui.theme.EduMilestoneTheme
import androidx.compose.runtime.DisposableEffect
import com.edumilestone.app.navigation.AppNavigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edumilestone.modules01.tools.features.ocr.ui.OCRScreen
import com.edumilestone.modules01.tools.features.pdf.ui.PDFScreen
import com.edumilestone.modules01.tools.features.word.ui.WordScreen
import androidx.activity.compose.BackHandler

class MainActivity : ComponentActivity() {
    // Declare AppNavigation instance to manage cleanup in the Activity lifecycle
    private val appNavigation = AppNavigation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EduMilestoneApp(appNavigation = appNavigation, finishActivity = { finish() })  // Pass appNavigation and finishActivity to the composable
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure cleanup when the Activity is destroyed
        appNavigation.cleanup()  // Call cleanup on appNavigation when composable is disposed
    }
}

@Composable
fun EduMilestoneApp(appNavigation: AppNavigation, finishActivity: () -> Unit) {
    val navController = rememberNavController() // Initialize NavController to handle navigation

    // Pass AppNavigation instance to manage cleanup when the composable is disposed
    DisposableEffect(Unit) {
        // Cleanup when the composable is disposed of
        onDispose {
            appNavigation.cleanup()  // Call cleanup on appNavigation when composable is disposed
        }
    }

    EduMilestoneTheme { // Apply the theme for the app
        // NavHost to handle navigation for different screens
        NavHost(navController = navController, startDestination = "home_screen") {
            composable("home_screen") {
                HomeScreen(navController = navController, appNavigation = appNavigation)
                // Handle back press on the Home screen
                BackHandler {
                    // You can add logic here if you want to control the back key behavior
                    // For example, exit app or handle any custom logic
                    // Uncomment below to exit the app when back is pressed from home screen
                    finishActivity() // Finish the activity when back is pressed from home screen
                }
            }
            composable("feature_screen/OCR") {
                OCRScreen() // Show OCRScreen when navigated to the OCR route
                // Handle back press on the OCR feature screen
                BackHandler {
                    // Unload the module before navigating back
                    appNavigation.unloadModuleForFeature("OCR")
                    navController.popBackStack("home_screen", inclusive = false) // Navigates back to the home screen
                }
            }
            composable("feature_screen/PDF") {
                PDFScreen() // Show PDFScreen when navigated to the PDF route
                // Handle back press on the PDF feature screen
                BackHandler {
                    // Unload the module before navigating back
                    appNavigation.unloadModuleForFeature("PDF")
                    navController.popBackStack("home_screen", inclusive = false) // Navigates back to the home screen
                }
            }
            composable("feature_screen/WORD") {
                WordScreen() // Show WordScreen when navigated to the WORD route
                // Handle back press on the Word feature screen
                BackHandler {
                    // Unload the module before navigating back
                    appNavigation.unloadModuleForFeature("WORD")
                    navController.popBackStack("home_screen", inclusive = false) // Navigates back to the home screen
                }
            }
        }
    }
}
