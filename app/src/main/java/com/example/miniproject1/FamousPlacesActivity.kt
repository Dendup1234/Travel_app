package com.example.miniproject1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FamousPlacesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var famousPlacesAdapter: FamousPlacesAdapter // You will need to create this adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_famous_places)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_famous_places)

        // Retrieve famous places from Intent
        val famousPlaces = intent.getStringArrayListExtra("famous_places") ?: arrayListOf()

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize and set the adapter
        famousPlacesAdapter = FamousPlacesAdapter(famousPlaces) // Pass famous places to adapter
        recyclerView.adapter = famousPlacesAdapter
    }
}
