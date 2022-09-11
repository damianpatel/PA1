package com.example.pa1.ui

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pa1.controller.WeatherApi
import java.io.IOException
import java.util.*

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
        // Label that displays temperature

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Body() {
    Column {
        // Remember state of entered value
        var textState by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        var temp = ""

        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Enter City Code") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )
        val context = LocalContext.current
        // Button to find temperature
        Button(onClick = {
            val city: String = textState
            val gc = Geocoder(context)
            if (Geocoder.isPresent()) {
                try {
                    val location = textState
                    val addresses = gc.getFromLocationName(location, 5)
                    val address = addresses[0]

                    val api = WeatherApi()
                    println(city)
                    val forecast = api.getWeatherData(address.latitude, address.longitude)
                    println(forecast)
                } catch(e: IOException) {

                }
            }

        }) {
            Text("Find Temperature")
        }
    }
}
