package com.example.miniproject1

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Set up the toolbar with a back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Initialize Google Places API with your API Key
        Places.initialize(applicationContext, "AIzaSyC_RWu3UTuilCVUGYZCPEvfCpViPnUQ-VE")

        // Initialize the AutocompleteSupportFragment
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment

        // Set the fields that you want the autocomplete to return
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Restrict search results to Bhutan (country code "BT") and only show addresses
        autocompleteFragment.setCountries("BT")
        autocompleteFragment.setTypeFilter(com.google.android.libraries.places.api.model.TypeFilter.ADDRESS)

        // Set up a PlaceSelectionListener for handling city search
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Move the map to the selected location
                val latLng = place.latLng
                if (latLng != null) {
                    mMap.clear()  // Clear any previous markers
                    mMap.addMarker(MarkerOptions().position(latLng).title(place.name))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                // Handle the error (optional)
            }
        })

        // Initialize the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check if coordinates are passed from the previous activity
        val cityName = intent.getStringExtra("CITY_NAME")
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)

        if (latitude != 0.0 && longitude != 0.0) {
            // Display the passed city on the map
            val cityLocation = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(cityLocation).title(cityName))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 15f))
        } else {
            // Default behavior: Set a default location in Bhutan
            val defaultLocation = LatLng(27.4712, 89.6339)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))
        }
    }

    // Handle back button in toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()  // Navigate back to the previous screen
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
