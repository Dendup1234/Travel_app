package com.example.miniproject1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MonumentsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var monumentsAdapter: MonumentsAdapter // You will need to create this adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monuments)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_monuments)

        // Retrieve monuments from Intent
        val monuments = intent.getStringArrayListExtra("monuments") ?: arrayListOf()

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize and set the adapter
        monumentsAdapter = MonumentsAdapter(monuments) // Pass monuments to adapter
        recyclerView.adapter = monumentsAdapter
    }
}
