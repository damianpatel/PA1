package com.example.pa1.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    val title = "Welcome to Temp Tracker"

    Column {
        // Title of the app
        Heading(title = title)
        // Main content area where user can enter 3-letter city code and retrieve temperature
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Body()
        }
    }
}

@Composable
fun Heading(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Body() {
    // Remember state of entered value
    var textState by remember { mutableStateOf("") }

    TextField(
        value = textState,
        onValueChange = { textState = it },
        label = { Text("Enter City Code") }
    )
}

