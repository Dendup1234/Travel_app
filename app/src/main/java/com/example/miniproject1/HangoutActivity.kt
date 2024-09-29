package com.example.miniproject1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HangoutActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hangoutAdapter: HangoutAdapter // You will need to create this adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangout)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_hangout)

        // Retrieve hangout spots from Intent
        val hangoutSpots = intent.getStringArrayListExtra("hangoutSpots") ?: arrayListOf()

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize and set the adapter
        hangoutAdapter = HangoutAdapter(hangoutSpots) // Pass hangout spots to adapter
        recyclerView.adapter = hangoutAdapter
    }
}
