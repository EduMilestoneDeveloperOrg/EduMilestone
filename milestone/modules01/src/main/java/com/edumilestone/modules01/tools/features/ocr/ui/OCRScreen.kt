package com.edumilestone.modules01.tools.features.ocr.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OCRScreen() {
    var extractedText by remember { mutableStateOf("") }
    var isTextPanelVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Light background color
                .padding(16.dp), // Padding around the screen
            horizontalAlignment = Alignment.CenterHorizontally, // Center align items horizontally
            verticalArrangement = Arrangement.Center // Center align items vertically
        ) {
            Icon(
                imageVector = Icons.Filled.Description,
                contentDescription = "OCR Icon",
                tint = Color(0xFF6200EE), // Primary color for the icon
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "OCR Feature",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE), // Primary color for the header
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Extract text from images easily with our OCR tool.",
                color = Color(0xFF757575), // Secondary color for the description
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Handle Camera capture */ },
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

                Button(
                    onClick = { /* Handle Gallery upload */ },
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

            Button(
                onClick = {
                    /* Handle text extraction */
                    extractedText = "Sample extracted text for demonstration purposes."
                    isTextPanelVisible = true
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
                    Text(
                        text = "Extracted Text:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { /* Handle copy text */ },
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

                        Button(
                            onClick = { /* Handle share text */ },
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