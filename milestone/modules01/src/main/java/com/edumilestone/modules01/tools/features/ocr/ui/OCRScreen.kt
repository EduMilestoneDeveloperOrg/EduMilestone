package com.edumilestone.modules01.tools.features.ocr.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import android.util.Log
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Import the AutoMirrored version

@Composable
fun OCRScreen(navController: NavController) {
    // State to hold the extracted text
    var extractedText by remember { mutableStateOf("") }
    // State to manage the visibility of the text panel
    var isTextPanelVisible by remember { mutableStateOf(false) }

    // Log the initial state
   // Log.d("OCRScreen", "OCRScreen composable started")

    // Animation state for the flicking effect
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Light background color
                .padding(16.dp), // Padding around the screen
            horizontalAlignment = Alignment.CenterHorizontally, // Center align items horizontally
            verticalArrangement = Arrangement.Center // Center align items vertically
        ) {
            // Modern styled back arrow button with flicking effect
            IconButton(
                onClick = {
                    Log.i("OCRScreen", "Back to Home button clicked")
                    navController.navigate("home_screen") {
                        popUpTo("home_screen") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .align(Alignment.Start)
                    .scale(scale) // Apply the flicking scale effect
                    .background(Color(0xFF6200EE), shape = CircleShape) // Modern circular background
                    .padding(8.dp) // Padding inside the button
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Updated to AutoMirrored version
                    contentDescription = "Back to Home",
                    tint = Color.White // White color for the icon
                )
            }

            // Icon for the OCR feature
            Icon(
                imageVector = Icons.Filled.Description,
                contentDescription = "OCR Icon",
                tint = Color(0xFF6200EE), // Primary color for the icon
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 16.dp)
            )

            // Header text for the OCR feature
            Text(
                text = "OCR Feature",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE), // Primary color for the header
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description text for the OCR feature
            Text(
                text = "Extract text from images easily with our OCR tool.",
                color = Color(0xFF757575), // Secondary color for the description
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Row for Camera and Gallery buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Button to capture image from camera
                Button(
                    onClick = {
                        Log.d("OCRScreen", "Camera button clicked")
                        // Add your camera capture logic here
                    },
                    shape = RoundedCornerShape(8.dp), // Rounded corners for the button
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)), // Primary color for the button
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Capture Image",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Camera",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Button to upload image from gallery
                Button(
                    onClick = {
                        Log.d("OCRScreen", "Gallery button clicked")
                        // Add your gallery upload logic here
                    },
                    shape = RoundedCornerShape(8.dp), // Rounded corners for the button
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)), // Primary color for the button
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhotoLibrary,
                        contentDescription = "Upload from Gallery",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Gallery",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Button to convert image to text
            Button(
                onClick = {
                    Log.i("OCRScreen", "Convert to Text button clicked")
                    extractedText = "Sample extracted text for demonstration purposes."
                    isTextPanelVisible = true
                    Log.d("OCRScreen", "Extracted text: $extractedText")
                },
                shape = RoundedCornerShape(8.dp), // Rounded corners for the button
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)), // Primary color for the button
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp) // Padding around the button
            ) {
                Icon(
                    imageVector = Icons.Filled.Description,
                    contentDescription = "Convert to Text",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp).padding(end = 8.dp)
                )
                Text(
                    text = "Convert to Text",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Panel to display the extracted text
        if (isTextPanelVisible) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .align(Alignment.CenterEnd)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column {
                    // Header for the extracted text panel
                    Text(
                        text = "Extracted Text:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Text field to display the extracted text
                    TextField(
                        value = extractedText,
                        onValueChange = { extractedText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color(0xFF6200EE),
                            unfocusedIndicatorColor = Color(0xFF757575)
                        )
                    )

                    // Row for Copy and Share buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Button to copy the extracted text
                        Button(
                            onClick = {
                                Log.i("OCRScreen", "Copy button clicked")
                                // Add your copy logic here
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text(
                                text = "Copy",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Button to share the extracted text
                        Button(
                            onClick = {
                                Log.i("OCRScreen", "Share button clicked")
                                // Add your share logic here
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                            modifier = Modifier.weight(1f).padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Share",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}