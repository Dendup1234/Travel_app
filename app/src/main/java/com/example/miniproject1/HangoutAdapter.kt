package com.example.miniproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HangoutAdapter(private val hangoutSpots: List<String>) :
    RecyclerView.Adapter<HangoutAdapter.HangoutViewHolder>() {

    class HangoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hangoutTextView: TextView = itemView.findViewById(android.R.id.text1) // Use built-in layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HangoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return HangoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: HangoutViewHolder, position: Int) {
        holder.hangoutTextView.text = hangoutSpots[position]
    }

    override fun getItemCount(): Int {
        return hangoutSpots.size
    }
}
