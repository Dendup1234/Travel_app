package com.example.miniproject1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TripDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "trips.db"
        private const val DATABASE_VERSION = 2 // Updated version
        private const val TABLE_NAME = "trips"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_IMAGE_URI = "imageUri" // New column for image URI
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_IMAGE_URI TEXT  -- Column for storing image URI as a string
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Only upgrade if the version is increased
        if (oldVersion < 2) {
            // Add the imageUri column to the existing table
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_IMAGE_URI TEXT")
        }
    }

    // Function to add a new trip to the database, including image URI
    fun addTrip(trip: Trip) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, trip.title)
            put(COLUMN_DATE, trip.date)
            put(COLUMN_IMAGE_URI, trip.imageUrl) // Store image URI as a string
        }
        trip.id = db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Function to retrieve all trips from the database, including image URIs
    fun getAllTrips(): List<Trip> {
        val trips = mutableListOf<Trip>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)) // Retrieve image URI
                trips.add(Trip(id, title, date, imageUri)) // Updated to include imageUri
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return trips
    }

    // Function to delete a trip by its ID
    fun deleteTrip(tripId: Long) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(tripId.toString()))
        db.close()
    }
}
