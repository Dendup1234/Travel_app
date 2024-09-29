package com.example.miniproject1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestaurantsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantsAdapter: RestaurantsAdapter // You will need to create this adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_restaurants)

        // Retrieve restaurants from Intent
        val restaurants = intent.getStringArrayListExtra("restaurants") ?: arrayListOf()

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize and set the adapter
        restaurantsAdapter = RestaurantsAdapter(restaurants) // Pass restaurants to adapter
        recyclerView.adapter = restaurantsAdapter
    }
}
