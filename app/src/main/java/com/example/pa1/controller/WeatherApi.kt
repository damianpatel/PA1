package com.example.pa1.controller

import okhttp3.*
import java.io.IOException

class WeatherApi {
    private final val apiURL: String = "https://i820e967xc.execute-api.us-east-1.amazonaws.com/test/helloworld?";
    private final val client = OkHttpClient();
    fun getWeatherData(latitude: Float, longitude: Float) {
        val request = Request.Builder().url(apiURL+"latitude=${latitude}&longitude=${longitude}").build();
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }
}