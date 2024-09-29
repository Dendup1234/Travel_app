package com.example.miniproject1

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface WeatherDao {
    @Insert
    suspend fun insertWeather(weatherData: WeatherData)
}
