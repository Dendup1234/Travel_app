package com.example.miniproject1

data class City(
    val name: String,
    val imageResId: Int, // This should be an Int referencing a drawable resource
    val latitude: Double,
    val longitude: Double
)
