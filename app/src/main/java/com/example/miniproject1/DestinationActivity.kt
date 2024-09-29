package com.example.miniproject1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationActivity : AppCompatActivity() {

    private lateinit var weatherTemperature: TextView
    private lateinit var weatherHumidity: TextView
    private lateinit var weatherStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        // Initialize UI elements
        val backIcon: ImageView = findViewById(R.id.back_icon)
        val cityName: TextView = findViewById(R.id.city_name)
        val cityImage: ImageView = findViewById(R.id.city_image)
        val cityDescription: TextView = findViewById(R.id.city_description)

        // Weather section
        weatherTemperature = findViewById(R.id.weather_temperature)
        weatherHumidity = findViewById(R.id.weather_humidity)
        weatherStatus = findViewById(R.id.weather_status)

        // Get data from Intent (passed from HomeActivity)
        val cityNameStr = intent.getStringExtra(HomeActivity.CITY_NAME_KEY)
        val cityImageResId = intent.getIntExtra(HomeActivity.CITY_IMAGE_RES_ID_KEY, 0)

        // Update UI with received data
        cityName.text = cityNameStr
        cityImageResId.takeIf { it != 0 }?.let {
            cityImage.setImageResource(it)
        }
        cityDescription.text = "$cityNameStr" // You can set the actual description here

        // Fetch real weather data
        val apiKey = "787428212e55e192c65884b67e2991fa" // Replace with your OpenWeatherMap API key
        val countryCode = "BT" // Replace with the appropriate country code for your needs
        fetchWeatherData(cityNameStr ?: "Bhutan", countryCode, apiKey)

        // Set up click listeners for back navigation and other cards
        setupClickListeners()

        // Back button functionality
        backIcon.setOnClickListener {
            finish()
        }
    }

    private fun fetchWeatherData(city: String, country: String, apiKey: String) {
        val cityWithCountry = "$city,$country"

        RetrofitClient.weatherApi.getWeather(cityWithCountry, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        updateWeatherUI(weatherResponse)
                    }
                } else {
                    Log.e("WeatherAPI", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherAPI", "Failure: ${t.message}")
            }
        })
    }

    private fun updateWeatherUI(weatherResponse: WeatherResponse) {
        val temperature = weatherResponse.main.temp
        val humidity = weatherResponse.main.humidity
        val weatherDescription = weatherResponse.weather[0].description

        weatherTemperature.text = "${temperature}°C"
        weatherHumidity.text = "Humidity: ${humidity}%"
        weatherStatus.text = weatherDescription.capitalize()
    }
    private fun getCityDetails(cityName: String): CityDescription? {
        val cityList = listOf(
            CityDescription(
                name = "Paro",
                imageResId = R.drawable.img_1,  // Placeholder image resource
                funFacts = listOf(
                    FunFact("Tiger's Nest Monastery", "Perched on a cliff at 3,120 meters, this iconic monastery is a must-visit for its stunning views."),
                    FunFact("Paro Dzong", "Also known as Rinpung Dzong, it serves as the administrative and religious center of Paro district."),
                    FunFact("Paro Tsechu", "An annual festival held in honor of Guru Rinpoche, featuring vibrant mask dances and cultural performances."),
                    FunFact("National Museum of Bhutan", "Housed in a traditional watchtower, this museum offers insights into Bhutan's rich cultural heritage.")
                ),
                hangoutSpots = listOf(
                    HangoutSpot("Paro Town", "A lively area perfect for shopping and dining.", "Paro Town Center", R.drawable.paro_town),
                    HangoutSpot("National Museum", "Explore Bhutan's art, culture, and history.", "Paro Dzongkhag", R.drawable.national_museum),
                    HangoutSpot("Rinpung Dzong", "A beautiful fortress that offers panoramic views.", "Rinpung Dzong", R.drawable.rinpung_dzong),
                    HangoutSpot("Zuri Dzong", "A hiking spot with breathtaking views of the valley.", "Above Paro", R.drawable.zuri_dzong),
                    HangoutSpot("Chuzom", "The confluence of Paro and Thimphu rivers, known for its beautiful stupa.", "Near Paro", R.drawable.chuzom)
                ),
                shoppingPlaces = listOf(
                    ShoppingItem("Paro Market", R.drawable.arts, "Famous for local crafts and souvenirs.", "https://bhutanyueselhandicraft.com/"),
                    ShoppingItem("Arts and Craft Shops", R.drawable.handicraft, "Various handicraft shops showcasing Bhutanese culture.", "https://www.tripadvisor.com/Attraction_Review-g321541-d2302215-Reviews-Chencho_Handicrafts-Paro_Paro_District.html"),
                    ShoppingItem("Paro Shops", R.drawable.masks, "Different types of products available.", "https://www.holidify.com/pages/shopping-in-bhutan-2576.html")
                ),
                famousPlaces = listOf(
                    FamousPlace("Tiger's Nest", "A famous monastery offering stunning views.", "Taktsang Monastery", R.drawable.tigers_nest, "https://en.wikipedia.org/wiki/Taktsang_Lakhang"),
                    FamousPlace("Rinpung Dzong", "A fortress-monastery that is a symbol of Paro.", "Paro Dzongkhag", R.drawable.rinpung_dzong, "https://www.bhutan.travel/destinations/paro/rinpung-dzong")
                ),
                monuments = listOf(
                    Monument("Kyichu Lhakhang", "One of the oldest temples in Bhutan, built in the 7th century.", "Paro", R.drawable.kyichu_lhakhang, "https://example.com/kyichu-lhakhang"),
                    Monument("Drukgyel Dzong", "A historic fortress built in 1647.", "Paro", R.drawable.dd, null)
                ),

                restaurants = listOf(
                    Restaurant("Sonam Trophel Restaurant", "Known for its authentic Bhutanese cuisine.", "paro", R.drawable.restaurant_1 ),
                    Restaurant("Lingka Restaurant", "A great place for both local and international dishes.", "paro", R.drawable.restaurant_2 ),

            )
            ),
            CityDescription(
                name = "Thimphu",
                imageResId = R.drawable.img,  // Placeholder image resource
                funFacts = listOf(
                    FunFact("No Traffic Lights", "Thimphu is one of the only capitals without traffic lights, relying instead on roundabouts and police officers to manage traffic."),
                    FunFact("Buddha Dordenma", "This colossal statue of Buddha is located atop a hill and is one of the largest in the world."),
                    FunFact("Tashichho Dzong", "The seat of the national government and the central monastic body, Tashichho Dzong is an impressive fortress."),
                    FunFact("National Library of Bhutan", "Home to ancient Buddhist texts and manuscripts, it serves as a repository of Bhutanese culture.")
                ),
                hangoutSpots = listOf(
                    HangoutSpot("Clock Tower", "A charming clock tower surrounded by cafes and shops.", "Thimphu Town Center", R.drawable.clock_tower),
                    HangoutSpot("Changlimithang Stadium", "A popular spot for archery and other traditional sports.", "Changlimithang", R.drawable.changlimithang_stadium),
                    HangoutSpot("Buddha Dordenma", "An impressive site with panoramic views of the city.", "Above Thimphu", R.drawable.buddha_point),
                    HangoutSpot("Dechencholing Palace", "A beautiful palace surrounded by gardens, perfect for a leisurely stroll.", "Thimphu", R.drawable.dechencholing_palace),
                    HangoutSpot("Thimphu Weekend Market", "A lively market where you can find local handicrafts and fresh produce.", "Thimphu", R.drawable.weekend_market_image)
                ),
                shoppingPlaces = listOf(
                    ShoppingItem("Weekend Market", R.drawable.weekend_market_image, "A lively market with local produce and crafts.", "https://traveltriangle.com/bhutan-tourism/thimphu/things-to-do/weekend-market"),
                    ShoppingItem("Norzin Lam Shops", R.drawable.norzin_lam_image, "Shops offering local and imported goods.", "https://www.flickr.com/photos/kartografia/4817546705")
                ),
                famousPlaces = listOf(
                    FamousPlace("Buddha Dordenma", "A colossal statue of Buddha.", "Above Thimphu", R.drawable.buddha_point, "https://en.wikipedia.org/wiki/Buddha_Dordenma_statue"),
                    FamousPlace("Tashichho Dzong", "The central monastic body and government seat.", "Thimphu", R.drawable.tashichho_dzong, "https://treasuryoflives.org/institution/Tashichoedzong")
                ),
                monuments = listOf(
                    Monument("Monument A", "Description of Monument A.", "Thimphu", R.drawable.buddha_point, "https://example.com/monument-a"),
                    Monument("Monument B", "Description of Monument B.", "Thimphu", R.drawable.dechenphu, null) // Set to null if no website
                ),

                restaurants = listOf(
                    Restaurant("Restaurant A", "Known for its delicious Bhutanese food.", "Location A", R.drawable.restaurant_1),
                    Restaurant("Restaurant B", "Offers a variety of international cuisine.", "Location B", R.drawable.restaurant_2 ),
                )

            )
            )


        return cityList.find { it.name == cityName }
    }

    private fun setupClickListeners() {
        val cityNameStr = intent.getStringExtra(HomeActivity.CITY_NAME_KEY) ?: "Paro"
        val cityDetails = getCityDetails(cityNameStr)

        // Fun Facts Card
        findViewById<CardView>(R.id.card_fun_facts).setOnClickListener {
            cityDetails?.funFacts?.let { facts ->
                startActivity(Intent(this, FunFactsActivity::class.java).apply {
                    putStringArrayListExtra("funFacts", ArrayList(facts.map { it.title }))
                    putStringArrayListExtra(
                        "funFactDescriptions",
                        ArrayList(facts.map { it.description })
                    )
                    putExtra(HomeActivity.CITY_NAME_KEY, cityNameStr)
                })
            }
        }

        // Hangout Spots Card
        findViewById<CardView>(R.id.card_hangout).setOnClickListener {
            cityDetails?.hangoutSpots?.let { spots ->
                startActivity(Intent(this, HangoutActivity::class.java).apply {
                    putStringArrayListExtra(
                        "hangoutSpots",
                        ArrayList(spots.map { it.name })
                    ) // Names
                    putStringArrayListExtra(
                        "hangoutDescriptions",
                        ArrayList(spots.map { it.description })
                    ) // Descriptions
                    putIntegerArrayListExtra(
                        "hangoutImages",
                        ArrayList(spots.map { it.imageResId })
                    ) // Images
                    putExtra(HomeActivity.CITY_NAME_KEY, cityNameStr)
                })
            }
        }

        // Shopping Places Card
        findViewById<CardView>(R.id.card_shopping).setOnClickListener {
            cityDetails?.shoppingPlaces?.let { places ->
                startActivity(Intent(this, ShoppingActivity::class.java).apply {
                    putStringArrayListExtra(
                        "shoppingPlaces",
                        ArrayList(places.map { it.name })
                    ) // Names
                    putStringArrayListExtra(
                        "shoppingWebsites",
                        ArrayList(places.map { it.website })
                    ) // Websites
                    putIntegerArrayListExtra(
                        "shoppingImages",
                        ArrayList(places.map { it.imageResId })
                    ) // Images
                    putExtra(HomeActivity.CITY_NAME_KEY, cityNameStr)
                })
            }
        }

        // Famous Places Card
        findViewById<CardView>(R.id.card_famous_places).setOnClickListener {
            cityDetails?.famousPlaces?.let { places ->
                startActivity(Intent(this, FamousPlacesActivity::class.java).apply {
                    putStringArrayListExtra(
                        "famousPlaces",
                        ArrayList(places.map { it.name })
                    ) // Names
                    putStringArrayListExtra(
                        "famousWebsites",
                        ArrayList(places.map { it.website })
                    ) // Websites
                    putStringArrayListExtra(
                        "famousDescriptions",
                        ArrayList(places.map { it.description })
                    ) // Descriptions
                    putIntegerArrayListExtra(
                        "famousImages",
                        ArrayList(places.map { it.imageResId })
                    ) // Images
                    putExtra(HomeActivity.CITY_NAME_KEY, cityNameStr)
                })
            }
        }

        // Monuments Card
        findViewById<CardView>(R.id.card_monuments).setOnClickListener {
            cityDetails?.monuments?.let { monuments ->
                startActivity(Intent(this, MonumentsActivity::class.java).apply {
                    putStringArrayListExtra(
                        "monuments",
                        ArrayList(monuments.map { it.name })
                    ) // Names
                    putStringArrayListExtra(
                        "monumentWebsites",
                        ArrayList(monuments.mapNotNull { it.website })
                    )

                    putStringArrayListExtra(
                        "monumentDescriptions",
                        ArrayList(monuments.map { it.description })
                    ) // Descriptions
                    putIntegerArrayListExtra(
                        "monumentImages",
                        ArrayList(monuments.map { it.imageResId })
                    ) // Images
                    putExtra(HomeActivity.CITY_NAME_KEY, cityNameStr)
                })
            }
        }

        // Restaurants Card
        findViewById<CardView>(R.id.card_restaurants).setOnClickListener {
            cityDetails?.restaurants?.let { restaurants ->
                startActivity(Intent(this, RestaurantsActivity::class.java).apply {
                    putStringArrayListExtra(
                        "restaurants",
                        ArrayList(restaurants.map { it.name })
                    ) // Names
                    putStringArrayListExtra(
                        "restaurantDescriptions",
                        ArrayList(restaurants.map { it.description })
                    ) // Descriptions
                    putIntegerArrayListExtra(
                        "restaurantImages",
                        ArrayList(restaurants.map { it.imageResId })
                    ) // Images
                    putExtra(HomeActivity.CITY_NAME_KEY, cityNameStr)
                })
            }
        }
    }

        companion object {
        const val CITY_NAME_KEY = "CITY_NAME"
        const val CITY_IMAGE_RES_ID_KEY = "CITY_IMAGE_RES_ID"
    }
}
