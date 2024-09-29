package com.example.miniproject1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FunFactsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var funFactsAdapter: FunFactsAdapter
    private var funFactsList: List<String> = listOf() // Change type to List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun_facts)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_fun_facts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve fun facts from Intent
        funFactsList = intent.getStringArrayListExtra("funFacts") ?: listOf()

        // Set up the adapter
        funFactsAdapter = FunFactsAdapter(funFactsList)
        recyclerView.adapter = funFactsAdapter
    }
}
