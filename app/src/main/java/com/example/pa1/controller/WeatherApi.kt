package com.example.pa1.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CompletableFuture


class WeatherApi {
    private final val apiURL: String = "https://i820e967xc.execute-api.us-east-1.amazonaws.com/test/helloworld?";
    private final val client = OkHttpClient()
    @RequiresApi(Build.VERSION_CODES.N)
    fun getWeatherData(latitude: Double, longitude: Double): String {
        val request = Request.Builder().url(apiURL+"latitude=${latitude}&longitude=${longitude}").build()
        var weatherData = ""
        val future = CallbackFuture()
        client.newCall(request).enqueue(future)
        val response = future.get()
        var gson = Gson()
        var message = response?.body()?.string()
        var forecast = gson.fromJson(message, Parser::class.java)
        weatherData = forecast.message.periods[0].detailedForecast
        return weatherData
    }
}

data class Parser(@SerializedName("message") val message: Message)

data class Message (@SerializedName("periods") val periods: List<Period>)

data class Period(@SerializedName("detailedForecast") val detailedForecast: String)

@RequiresApi(Build.VERSION_CODES.N)
internal class CallbackFuture : CompletableFuture<Response?>(),
    Callback {
    override fun onResponse(call: Call?, response: Response?) {
        super.complete(response)
    }

    override fun onFailure(call: Call?, e: IOException?) {
        super.completeExceptionally(e)
    }
}
