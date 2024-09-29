package com.example.miniproject1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button by its ID
        val buttonEnter = findViewById<Button>(R.id.buttonEnter)

        // Set an onClickListener to handle the click event
        buttonEnter.setOnClickListener {
            // Create an Intent to start SecondActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
