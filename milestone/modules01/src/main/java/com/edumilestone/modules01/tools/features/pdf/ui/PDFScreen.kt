package com.edumilestone.modules01.tools.features.pdf.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PDFScreen() {
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
            contentDescription = "PDF Icon",
            tint = Color(0xFF6200EE), // Primary color for the icon
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "PDF Viewer",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE), // Primary color for the header
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Upcoming Features Under Tools_Section",
            color = Color(0xFF757575), // Secondary color for the description
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Development in progress",
            color = Color(0xFF757575), // Secondary color for the description
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { /* Handle action if needed */ },
            shape = RoundedCornerShape(8.dp), // Rounded corners for the button
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)), // Primary color for the button
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp) // Padding around the button
        ) {
            Text(
                text = "Back to Home",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}