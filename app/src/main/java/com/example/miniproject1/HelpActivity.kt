package com.example.miniproject1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Button for App Tutorial
        findViewById<Button>(R.id.btnAppTutorial).setOnClickListener {
            // Navigate to App Tutorial or Guide Screen
            startActivity(intent)
        }

        // Button for FAQs
        findViewById<Button>(R.id.btnFAQs).setOnClickListener {
            // Show FAQs in a dialog or navigate to FAQ Activity
            startActivity(intent)
        }

        // Button for Contact Support
        findViewById<Button>(R.id.btnContactSupport).setOnClickListener {
            // Open an email client for contacting support
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:support@example.com")
                putExtra(Intent.EXTRA_SUBJECT, "Support Request")
            }
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show()
            }
        }

        // Button for Terms & Conditions
        findViewById<Button>(R.id.btnTermsConditions).setOnClickListener {
            // Show terms and conditions
            startActivity(intent)
        }

        // Button for App Version Information
        findViewById<Button>(R.id.btnAppVersion).setOnClickListener {
            // Display app version info using Toast or Dialog
            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            Toast.makeText(this, "App Version: $versionName", Toast.LENGTH_SHORT).show()
        }
    }
}
