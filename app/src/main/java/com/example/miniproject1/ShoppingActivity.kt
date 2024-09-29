package com.example.miniproject1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShoppingActivity : AppCompatActivity() {

    private lateinit var shoppingPlaces: ArrayList<String>
    private lateinit var shoppingWebsites: ArrayList<String>
    private lateinit var shoppingImages: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable Up button
        supportActionBar?.setDisplayShowHomeEnabled(true) // Show home button

        // Retrieve data passed from the previous activity
        shoppingPlaces = intent.getStringArrayListExtra("shoppingPlaces") ?: arrayListOf()
        shoppingWebsites = intent.getStringArrayListExtra("shoppingWebsites") ?: arrayListOf()
        shoppingImages = intent.getIntegerArrayListExtra("shoppingImages")?.map { it } ?: listOf()

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_shopping_places)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ShoppingAdapter(shoppingPlaces, shoppingWebsites, shoppingImages)
    }

    // Handle the Up button action
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Call back pressed to navigate up
        return true
    }
}
