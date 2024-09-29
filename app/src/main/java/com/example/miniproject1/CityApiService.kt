package com.example.miniproject1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {
    @GET("city/monuments")  // Adjust the endpoint path accordingly
    fun getMonumentsByCity(@Query("city") city: String): Call<List<Monument>>
}
