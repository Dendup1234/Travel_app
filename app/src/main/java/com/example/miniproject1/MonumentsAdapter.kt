package com.example.miniproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MonumentsAdapter(private val monuments: List<String>) :
    RecyclerView.Adapter<MonumentsAdapter.MonumentsViewHolder>() {

    class MonumentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monumentTextView: TextView = itemView.findViewById(android.R.id.text1) // Use built-in layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MonumentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonumentsViewHolder, position: Int) {
        holder.monumentTextView.text = monuments[position]
    }

    override fun getItemCount(): Int {
        return monuments.size
    }
}
