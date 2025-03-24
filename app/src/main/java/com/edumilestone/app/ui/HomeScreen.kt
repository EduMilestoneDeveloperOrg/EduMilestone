package com.edumilestone.app.ui

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edumilestone.app.navigation.AppNavigation

@Composable
fun HomeScreen(navController: NavController, appNavigation: AppNavigation) {
    Log.d("HomeScreen", "HomeScreen composable started")

    // State to manage the expanded/collapsed state of the menu
    var menuExpanded by remember { mutableStateOf(false) }
    // Animate the width of the menu based on its expanded/collapsed state
    val menuWidth by animateDpAsState(targetValue = if (menuExpanded) 200.dp else 0.dp)

    Box(modifier = Modifier.fillMaxSize()) {
        // Main Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    menuExpanded = !menuExpanded // Toggle menu state on click
                    Log.d("HomeScreen", "Menu toggled: $menuExpanded")
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Gradient text for welcome message
                GradientText(
                    text = "Welcome to EduMilestone!",
                    gradient = Brush.linearGradient(
                        colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5), Color(0xFFFF0266))
                    ),
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 24.sp
                )
                // Gradient text for milestone module
                GradientText(
                    text = "Milestone Module 01",
                    gradient = Brush.linearGradient(
                        colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5), Color(0xFFFF0266))
                    ),
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontSize = 24.sp
                )
                // Gradient text for products section
                GradientText(
                    text = "Products: Tools Section",
                    gradient = Brush.linearGradient(
                        colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5), Color(0xFFFF0266))
                    ),
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontSize = 24.sp
                )

                // Tool buttons for different features
                ToolButton("OCR", Color(0xFFA5D6A7)) {
                    Log.d("HomeScreen", "OCR button clicked")
                    appNavigation.handleFeatureRequest(navController, "OCR")
                }
                ToolButton("PDF", Color(0xFFFFCDD2)) {
                    Log.d("HomeScreen", "PDF button clicked")
                    appNavigation.handleFeatureRequest(navController, "PDF")
                }
                ToolButton("WORD", Color(0xFF81D4FA)) {
                    Log.d("HomeScreen", "WORD button clicked")
                    appNavigation.handleFeatureRequest(navController, "WORD")
                }
            }
        }

        // Sliding Menu
        if (menuExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                    .clickable { menuExpanded = false } // Collapse menu on background click
            ) {
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
                    MenuItem("Home") { Log.d("HomeScreen", "Home menu item clicked") }
                    MenuItem("Settings") { Log.d("HomeScreen", "Settings menu item clicked") }
                    MenuItem("Profile") { Log.d("HomeScreen", "Profile menu item clicked") }
                    MenuItem("Tools") { Log.d("HomeScreen", "Tools menu item clicked") }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        MenuItem("OCR") {
                            Log.d("HomeScreen", "OCR menu item clicked")
                            appNavigation.handleFeatureRequest(navController, "OCR")
                            menuExpanded = false // Collapse the menu after navigation
                        }
                        MenuItem("PDF") {
                            Log.d("HomeScreen", "PDF menu item clicked")
                            appNavigation.handleFeatureRequest(navController, "PDF")
                            menuExpanded = false // Collapse the menu after navigation
                        }
                        MenuItem("WORD") {
                            Log.d("HomeScreen", "WORD menu item clicked")
                            appNavigation.handleFeatureRequest(navController, "WORD")
                            menuExpanded = false // Collapse the menu after navigation
                        }
                    }
                }
            }
        }

        // Floating Action Buttons for Tools
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                onClick = {
                    Log.d("HomeScreen", "OCR FAB clicked")
                    appNavigation.handleFeatureRequest(navController, "OCR")
                },
                modifier = Modifier.padding(bottom = 16.dp),
                containerColor = Color(0xFFA5D6A7)
            ) {
                Text("OCR", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            FloatingActionButton(
                onClick = {
                    Log.d("HomeScreen", "PDF FAB clicked")
                    appNavigation.handleFeatureRequest(navController, "PDF")
                },
                modifier = Modifier.padding(bottom = 16.dp),
                containerColor = Color(0xFFFFCDD2)
            ) {
                Text("PDF", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            FloatingActionButton(
                onClick = {
                    Log.d("HomeScreen", "WORD FAB clicked")
                    appNavigation.handleFeatureRequest(navController, "WORD")
                },
                containerColor = Color(0xFF81D4FA)
            ) {
                Text("WORD", color = Color.Black, fontWeight = FontWeight.Bold)
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

/**
 * GradientText composable displays text with a gradient color.
 * @param text The text to display.
 * @param gradient The gradient brush to apply to the text.
 * @param modifier The modifier to apply to the text.
 * @param fontSize The font size of the text.
 */
@Composable
fun GradientText(text: String, gradient: Brush, modifier: Modifier = Modifier, fontSize: TextUnit) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(brush = gradient)) {
                append(text)
            }
        },
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}