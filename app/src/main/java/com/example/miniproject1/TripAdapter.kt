package com.example.miniproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TripAdapter(
    private val tripList: MutableList<Trip>,
    private val onDeleteClick: (Int) -> Unit // Function to handle delete action
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false) // item_trip is your item layout XML
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = tripList[position]
        holder.bind(trip)

        // Set up delete button click action
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position) // Call the delete function with the current position
        }
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    inner class TripViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.tripTitle)
        val dateText: TextView = view.findViewById(R.id.tripDate)
        val tripImage: ImageView = view.findViewById(R.id.tripImage)
        val deleteButton: ImageView = view.findViewById(R.id.deleteButton) // Ensure the deleteButton ID is correct

        fun bind(trip: Trip) {
            titleText.text = trip.title
            dateText.text = trip.date

            // Load the trip image using Glide (or any image loading library)
            Glide.with(tripImage.context)
                .load(trip.imageUrl) // Make sure trip.imageUrl contains the correct image URL
                .placeholder(R.drawable.ic_placeholder) // Show placeholder while loading
                .into(tripImage)
        }
    }
}
