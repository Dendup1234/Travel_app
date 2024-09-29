package com.example.miniproject1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "weather.db"
        private const val DATABASE_VERSION = 1

        // City table
        const val TABLE_CITY_INFO = "city_info"
        const val COLUMN_CITY_ID = "id"
        const val COLUMN_CITY_NAME = "name"
        const val COLUMN_CITY_LATITUDE = "latitude"
        const val COLUMN_CITY_LONGITUDE = "longitude"
        const val COLUMN_CITY_IMAGE_RES_ID = "imageResId"

        // Weather table
        const val TABLE_WEATHER_DATA = "weather_data"
        const val COLUMN_WEATHER_ID = "id"
        const val COLUMN_WEATHER_CITY_ID = "cityId"
        const val COLUMN_WEATHER_TEMPERATURE = "temperature"
        const val COLUMN_WEATHER_HUMIDITY = "humidity"
        const val COLUMN_WEATHER_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create City Info table
        val createCityTable = """
            CREATE TABLE $TABLE_CITY_INFO (
                $COLUMN_CITY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CITY_NAME TEXT NOT NULL,
                $COLUMN_CITY_LATITUDE REAL NOT NULL,
                $COLUMN_CITY_LONGITUDE REAL NOT NULL,
                $COLUMN_CITY_IMAGE_RES_ID INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createCityTable)

        // Create Weather Data table
        val createWeatherTable = """
            CREATE TABLE $TABLE_WEATHER_DATA (
                $COLUMN_WEATHER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_WEATHER_CITY_ID INTEGER NOT NULL,
                $COLUMN_WEATHER_TEMPERATURE REAL NOT NULL,
                $COLUMN_WEATHER_HUMIDITY INTEGER NOT NULL,
                $COLUMN_WEATHER_DESCRIPTION TEXT NOT NULL,
                FOREIGN KEY($COLUMN_WEATHER_CITY_ID) REFERENCES $TABLE_CITY_INFO($COLUMN_CITY_ID)
            )
        """.trimIndent()
        db.execSQL(createWeatherTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WEATHER_DATA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CITY_INFO")
        onCreate(db)
    }

    // Insert city info
    fun insertCity(cityInfo: CityInfo): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CITY_NAME, cityInfo.name)
            put(COLUMN_CITY_LATITUDE, cityInfo.latitude)
            put(COLUMN_CITY_LONGITUDE, cityInfo.longitude)
            put(COLUMN_CITY_IMAGE_RES_ID, cityInfo.imageResId)
        }
        return db.insert(TABLE_CITY_INFO, null, values)
    }

    // Insert weather data
    fun insertWeather(weatherData: WeatherData): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_WEATHER_CITY_ID, weatherData.cityId)
            put(COLUMN_WEATHER_TEMPERATURE, weatherData.temperature)
            put(COLUMN_WEATHER_HUMIDITY, weatherData.humidity)
            put(COLUMN_WEATHER_DESCRIPTION, weatherData.description)
        }
        return db.insert(TABLE_WEATHER_DATA, null, values)
    }

    // Get city details
    fun getCityDetails(cityId: Int): CityInfo? {
        val db = readableDatabase
        val cursor: Cursor? = db.query(
            TABLE_CITY_INFO, null, "$COLUMN_CITY_ID = ?",
            arrayOf(cityId.toString()), null, null, null
        )

        cursor?.use {
            return if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_CITY_ID))
                val name = it.getString(it.getColumnIndexOrThrow(COLUMN_CITY_NAME))
                val latitude = it.getDouble(it.getColumnIndexOrThrow(COLUMN_CITY_LATITUDE))
                val longitude = it.getDouble(it.getColumnIndexOrThrow(COLUMN_CITY_LONGITUDE))
                val imageResId = it.getInt(it.getColumnIndexOrThrow(COLUMN_CITY_IMAGE_RES_ID))
                CityInfo(id, name, latitude, longitude, imageResId, "some Fun Facts")
            } else {
                null
            }
        } ?: run {
            // Handle case where cursor is null (e.g., table doesn't exist)
            return null
        }
    }

    // Get all cities
    fun getAllCities(): List<CityInfo> {
        val cityList = mutableListOf<CityInfo>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_CITY_INFO", null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_CITY_ID))
                    val name = it.getString(it.getColumnIndexOrThrow(COLUMN_CITY_NAME))
                    val latitude = it.getDouble(it.getColumnIndexOrThrow(COLUMN_CITY_LATITUDE))
                    val longitude = it.getDouble(it.getColumnIndexOrThrow(COLUMN_CITY_LONGITUDE))
                    val imageResId = it.getInt(it.getColumnIndexOrThrow(COLUMN_CITY_IMAGE_RES_ID))
                    cityList.add(CityInfo(id, name, latitude, longitude, imageResId, "some Fun Facts"))
                } while (it.moveToNext())
            }
        } ?: run {
            // Handle case where cursor is null (e.g., table doesn't exist)
        }

        return cityList
    }

    // Get weather data for a city
    fun getWeatherData(cityId: Int): WeatherData? {
        val db = readableDatabase
        val cursor: Cursor? = db.query(
            TABLE_WEATHER_DATA, null, "$COLUMN_WEATHER_CITY_ID = ?",
            arrayOf(cityId.toString()), null, null, null
        )

        cursor?.use {
            return if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_WEATHER_ID))
                val temperature = it.getDouble(it.getColumnIndexOrThrow(COLUMN_WEATHER_TEMPERATURE))
                val humidity = it.getInt(it.getColumnIndexOrThrow(COLUMN_WEATHER_HUMIDITY))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_WEATHER_DESCRIPTION))
                WeatherData(id, cityId, temperature, humidity.toString(), description)
            } else {
                null
            }
        } ?: run {
            // Handle case where cursor is null (e.g., table doesn't exist)
            return null
        }
    }
}
