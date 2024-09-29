package com.example.miniproject1

data class Trip(
    var id: Long = 0,
    var title: String,
    var date: String,
    var imageUrl: String? = null // Optional field for image URI
)
