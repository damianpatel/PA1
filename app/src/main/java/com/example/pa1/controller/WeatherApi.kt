package com.example.pa1.controller

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.*
import java.io.IOException

class WeatherApi {
    private final val apiURL: String = "https://i820e967xc.execute-api.us-east-1.amazonaws.com/test/helloworld?";
    private final val client = OkHttpClient()
    fun getWeatherData(latitude: Double, longitude: Double): String {
        println("Im here")
        println(latitude)
        println(longitude)
        val request = Request.Builder().url(apiURL+"latitude=${latitude}&longitude=${longitude}").build()
        var weatherData = ""
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                println("After request")
                val g = Gson()
                val o = g.fromJson(response.body()?.string(), Parser::class.java)
                println("After creating gson parser")
                response.body()?.close()
                println("After closing body")
                weatherData = o.message.periods[0].detailedForecast
                println("After getting detailed forecast")
                println(weatherData)
            }
        })
        return weatherData
    }
}

data class Parser(@SerializedName("message") val message: Message)

data class Message (@SerializedName("periods") val periods: List<Period>)

data class Period(@SerializedName("detailedForecast") val detailedForecast: String)
