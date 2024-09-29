package com.example.miniproject1

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject1.DestinationActivity.Companion.CITY_IMAGE_RES_ID_KEY
import com.example.miniproject1.DestinationActivity.Companion.CITY_NAME_KEY

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cityList: List<City>
    private lateinit var filteredCityList: MutableList<City>
    private lateinit var adapter: CityAdapter
    private lateinit var buttonDetails: Button
    private lateinit var buttonViewMap: Button
    private lateinit var cityImage: ImageView
    private lateinit var cityName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize UI elements
        initializeUI()

        // Setup button functionalities
        setupButtonActions()

        // Setup search functionality
        setupSearchFunctionality()

        // Setup menu functionality
        setupMenuFunctionality()
    }

    private fun initializeUI() {
        recyclerView = findViewById(R.id.recycler_view)
        buttonDetails = findViewById(R.id.know_more)
        buttonViewMap = findViewById(R.id.view_on_map_button)
        cityImage = findViewById(R.id.city_image)
        cityName = findViewById(R.id.city_name)

        recyclerView.visibility = View.GONE
        cityImage.visibility = View.GONE // Hide city image by default
        cityName.visibility = View.GONE // Hide city name by default

        // Initialize city list
        cityList = listOf(
            City("Thimphu", R.drawable.img, 27.472792, 89.639286),
            City("Paro", R.drawable.img_1, 27.4305, 89.4136),
            City("Punakha", R.drawable.img_2, 27.5916, 89.8775),
            City("Haa", R.drawable.img_3, 27.3848, 89.2801),
            City("Bumthang", R.drawable.img_4, 26.8645, 89.405),
            City("Dagana", R.drawable.img_5, 27.0917, 89.8874),
            City("Gasa", R.drawable.img_6, 28.026, 89.7295),
            City("Lhuentse", R.drawable.img_7, 27.6675, 91.1837),
            City("Mongar", R.drawable.img_8, 27.2745, 91.2404),
            City("Pemagatshel", R.drawable.img_9, 27.0375, 91.4039),
            City("Samdrup Jongkhar", R.drawable.img_10, 26.7984, 91.5092),
        )


        filteredCityList = cityList.toMutableList()

        // Setup RecyclerView with adapter
        adapter = CityAdapter(filteredCityList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupButtonActions() {
        // Action for the "Know More" button
        buttonDetails.setOnClickListener {
            handleCitySelection { city ->
                // Create an Intent to start the DestinationActivity
                val intent = Intent(this, DestinationActivity::class.java).apply {
                    putExtra(CITY_NAME_KEY, city.name)
                    putExtra(CITY_IMAGE_RES_ID_KEY, city.imageResId)
                }
                startActivity(intent)
            }
        }

        // Action for the "View Map" button
        buttonViewMap.setOnClickListener {
            handleCitySelection { city ->
                val intent = Intent(this, MapsActivity::class.java).apply {
                    putExtra("CITY_NAME", city.name)
                    putExtra("LATITUDE", city.latitude)
                    putExtra("LONGITUDE", city.longitude)
                }
                startActivity(intent)
            }
        }

        // Set onClickListener for city image to navigate to the details view
        cityImage.setOnClickListener {
            handleCitySelection { city ->
                val intent = Intent(this, DestinationActivity::class.java).apply {
                    putExtra(CITY_NAME_KEY, city.name)
                    putExtra(CITY_IMAGE_RES_ID_KEY, city.imageResId)
                }
                startActivity(intent)
            }
        }
    }

    private fun handleCitySelection(action: (City) -> Unit) {
        if (filteredCityList.size == 1) {
            action(filteredCityList[0]) // Perform action with the selected city
        } else {
            Toast.makeText(this, "Please search for a specific city first.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupSearchFunctionality() {
        val searchEditText: EditText = findViewById(R.id.search_city)
        val searchButton: ImageView = findViewById(R.id.search_button)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            filterCities(query)
        }

        // Optional: respond to "Enter" key press on the keyboard
        searchEditText.setOnEditorActionListener { _, _, _ ->
            val query = searchEditText.text.toString().trim()
            filterCities(query)
            true
        }
    }

    private fun filterCities(query: String) {
        filteredCityList.clear()

        if (query.isEmpty()) {
            filteredCityList.addAll(cityList) // Show all cities when there's no query
            hideCityDetails()
        } else {
            val filtered = cityList.filter { city -> city.name.contains(query, ignoreCase = true) }
            filteredCityList.addAll(filtered)

            if (filteredCityList.isEmpty()) {
                Toast.makeText(this, "No cities found matching your query", Toast.LENGTH_SHORT)
                    .show()
                hideCityDetails()
            } else {
                recyclerView.visibility = View.VISIBLE
                showCityDetails(filteredCityList[0])
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun hideCityDetails() {
        recyclerView.visibility = View.GONE
        cityImage.visibility = View.GONE
        cityName.visibility = View.GONE
    }

    private fun showCityDetails(city: City) {
        cityImage.setImageResource(city.imageResId)
        cityName.text = city.name
        cityImage.visibility = View.VISIBLE
        cityName.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun setupMenuFunctionality() {
        val menuIcon: ImageView = findViewById(R.id.menu_icon)

        menuIcon.setOnClickListener {
            val popupMenu = PopupMenu(this, menuIcon)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu_main, popupMenu.menu)

            try {
                val field = popupMenu.javaClass.getDeclaredField("mPopup")
                field.isAccessible = true
                val menuPopupHelper = field.get(popupMenu)
                val method = menuPopupHelper.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                method.invoke(menuPopupHelper, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.home -> {
                        startActivity(Intent(this, HomeActivity::class.java))
                        true
                    }

                    R.id.AddTrips -> {
                        startActivity(Intent(this, TripActivity::class.java))
                        true
                    }

                    R.id.Checklist -> {
                        startActivity(Intent(this, ChecklistActivity::class.java))
                        true
                    }

                    R.id.action_help -> {
                        startActivity(Intent(this, HelpActivity::class.java))
                        true
                    }

                    R.id.settings -> {
                        startActivity(Intent(this, SettingsActivity::class.java))
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }
    }


    companion object {
        const val CITY_NAME_KEY = "CITY_NAME"
        const val CITY_IMAGE_RES_ID_KEY = "CITY_IMAGE_RES_ID"
    }
}
