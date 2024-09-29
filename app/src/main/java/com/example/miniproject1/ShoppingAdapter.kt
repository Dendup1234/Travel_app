package com.example.miniproject1

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter(
    private val shoppingPlaces: List<String>,
    private val shoppingWebsites: List<String>,
    private val shoppingImages: List<Int>
) : RecyclerView.Adapter<ShoppingAdapter.ShoppingPlaceViewHolder>() {

    class ShoppingPlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_shopping_place)
        val textView: TextView = view.findViewById(R.id.text_shopping_place)
        val readMore: TextView = view.findViewById(R.id.read_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping, parent, false)
        return ShoppingPlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingPlaceViewHolder, position: Int) {
        holder.textView.text = shoppingPlaces[position]
        holder.imageView.setImageResource(shoppingImages[position])

        // Set the "Read More" click listener
        holder.readMore.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(shoppingWebsites[position]))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return shoppingPlaces.size
    }
}
