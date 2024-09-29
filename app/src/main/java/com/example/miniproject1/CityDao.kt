package com.example.miniproject1

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM cities LIMIT 1") // Adjust as needed
    suspend fun getCityDetails(): CityInfo?
}
