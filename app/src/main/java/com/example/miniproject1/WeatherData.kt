package com.example.miniproject1

data class WeatherData(
    var id: Int = 0,
    var cityId: Int,
    var temperature: Double,
    var description: String,
    var humidity: String,
)
