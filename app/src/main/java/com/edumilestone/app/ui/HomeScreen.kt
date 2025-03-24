package com.edumilestone.app.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.edumilestone.app.navigation.AppNavigation

/**
 * HomeScreen composable displays the welcome screen and allows the user to select different tools (OCR, PDF, Word).
 * It also includes a sliding menu bar for additional navigation options.
 * It accepts a NavController to navigate between different screens.
 */
@Composable
fun HomeScreen(navController: NavController, appNavigation: AppNavigation) {
    // State to manage the expanded/collapsed state of the menu
    var menuExpanded by remember { mutableStateOf(false) }
    // Animate the width of the menu based on its expanded/collapsed state
    val menuWidth by animateDpAsState(targetValue = if (menuExpanded) 200.dp else 0.dp)

    Box(modifier = Modifier.fillMaxSize()) {
        // Sliding Menu
        Column(
            modifier = Modifier
                .width(menuWidth) // Set the width of the menu based on its state
                .fillMaxHeight() // Fill the height of the screen
                .background(Color(0xFF6200EE)) // Background color of the menu
                .padding(16.dp) // Padding inside the menu
        ) {
            Text(
                text = "Menu",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Menu items
            MenuItem("Home") { /* Handle Home click */ }
            MenuItem("Settings") { /* Handle Settings click */ }
            MenuItem("Profile") { /* Handle Profile click */ }
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable { menuExpanded = !menuExpanded }, // Toggle menu state on click
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to EduMilestone!",
                modifier = Modifier.padding(bottom = 20.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            Text(
                text = "Milestone Module 01",
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            Text(
                text = "Products: Tools Section",
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF6200EE)
            )

            // Tool buttons for different features
            ToolButton("OCR", Color(0xFFA5D6A7)) {
                appNavigation.handleFeatureRequest(navController, "OCR")
            }
            ToolButton("PDF", Color(0xFFFFCDD2)) {
                appNavigation.handleFeatureRequest(navController, "PDF")
            }
            ToolButton("WORD", Color(0xFF81D4FA)) {
                appNavigation.handleFeatureRequest(navController, "WORD")
            }
        }
    }
}

/**
 * MenuItem composable displays a clickable text item in the menu.
 * @param text The text to display for the menu item.
 * @param onClick The action to perform when the menu item is clicked.
 */
@Composable
fun MenuItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick) // Handle click action
    )
}

/**
 * ToolButton composable displays a button for each tool (OCR, PDF, Word).
 * The button is styled with a rounded shape and a background color.
 * The onClick lambda is executed when the button is clicked.
 * @param featureName The name of the feature to display on the button.
 * @param buttonColor The background color of the button.
 * @param onClick The action to perform when the button is clicked.
 */
@Composable
fun ToolButton(featureName: String, buttonColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick, // Executes the passed onClick action when the button is clicked
        modifier = Modifier
            .fillMaxWidth() // Button takes full width
            .padding(vertical = 8.dp), // Vertical padding for spacing between buttons
        shape = RoundedCornerShape(8.dp), // Rounded corners for the button
        contentPadding = PaddingValues(16.dp), // Padding inside the button
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor) // Button color
    ) {
        Text(
            text = featureName,
            color = Color.Black,
            fontWeight = FontWeight.Bold // Bold text for the button
        )
    }
}