package com.example.miniproject1
data class Monument(
    val name: String,
    val description: String,
    val location: String,
    val imageResId: Int,
    val website: String? // Ensure this is nullable if not all monuments have a website
)
