package com.example.miniproject1

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FunFactsAdapter(private val funFacts: List<String>) : RecyclerView.Adapter<FunFactsAdapter.FunFactViewHolder>() {

    inner class FunFactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val funFactTextView: TextView = itemView.findViewById(R.id.text_view_fun_fact)

        fun bind(funFact: String) {
            funFactTextView.text = funFact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunFactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fun_facts, parent, false)
        return FunFactViewHolder(view)
    }

    override fun onBindViewHolder(holder: FunFactViewHolder, position: Int) {
        holder.bind(funFacts[position])
    }

    override fun getItemCount(): Int = funFacts.size
}
