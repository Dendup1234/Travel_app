package com.example.miniproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(private val cityList: List<City>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityImage: ImageView = view.findViewById(R.id.city_image)
        val cityName: TextView = view.findViewById(R.id.city_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.cityName.text = city.name
        holder.cityImage.setImageResource(city.imageResId)
        // Add click listener to handle click events
        holder.itemView.setOnClickListener {
            // Handle city item click
        }
    }

    override fun getItemCount() = cityList.size
}
