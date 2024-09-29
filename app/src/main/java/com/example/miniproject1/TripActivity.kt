package com.example.miniproject1

import android.app.DatePickerDialog
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TripActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tripAdapter: TripAdapter
    private lateinit var addButton: Button
    private lateinit var tripDatabaseHelper: TripDatabaseHelper
    private var tripList: MutableList<Trip> = mutableListOf()
    private var selectedImageUri: Uri? = null

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        recyclerView = findViewById(R.id.recyclerViewTrips)
        addButton = findViewById(R.id.buttonAddTrip)

        tripDatabaseHelper = TripDatabaseHelper(this)

        // Initialize RecyclerView with GridLayoutManager for 2 columns
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        tripAdapter = TripAdapter(tripList) { position -> deleteTrip(position) }
        recyclerView.adapter = tripAdapter

        // Load saved trips from the database
        loadTrips()

        // Add trip button listener
        addButton.setOnClickListener {
            showAddTripDialog()
        }
    }

    private fun showAddTripDialog() {
        val titleInput = EditText(this).apply {
            hint = "Enter trip title"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        val dateInput = EditText(this).apply {
            hint = "Select trip date"
            inputType = InputType.TYPE_NULL // Prevent keyboard from appearing
            setOnClickListener { showDatePickerDialog(this) } // Open date picker dialog
        }

        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.ic_placeholder)
            setOnClickListener { openImagePicker() } // Open image picker
        }

        val dialogView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(titleInput)
            addView(dateInput)
            addView(imageView)
        }

        AlertDialog.Builder(this)
            .setTitle("Add New Trip")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val tripTitle = titleInput.text.toString()
                val tripDate = dateInput.text.toString()
                if (tripTitle.isNotEmpty() && tripDate.isNotEmpty() && selectedImageUri != null) {
                    // Save the image URI as a string (to save it to the database)
                    val newTrip = Trip(title = tripTitle, date = tripDate, imageUrl = selectedImageUri.toString())
                    tripDatabaseHelper.addTrip(newTrip)
                    tripList.add(newTrip)
                    tripAdapter.notifyItemInserted(tripList.size - 1)
                    Toast.makeText(this, "Trip added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDatePickerDialog(dateInput: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
            dateInput.setText(formattedDate) // Set the formatted date to the EditText
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data // Get the selected image URI
        }
    }

    private fun deleteTrip(position: Int) {
        val tripId = tripList[position].id
        tripDatabaseHelper.deleteTrip(tripId)
        tripList.removeAt(position)
        tripAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Trip deleted successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun loadTrips() {
        tripList.clear()
        tripList.addAll(tripDatabaseHelper.getAllTrips())
        tripAdapter.notifyDataSetChanged()
    }
}
