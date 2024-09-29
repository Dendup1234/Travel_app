package com.example.miniproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChecklistAdapter(
    private var checklistItems: MutableList<ChecklistItem>,
    private val onItemEdit: (Int) -> Unit,
    private val onItemDelete: (Int) -> Unit
) : RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {

    class ChecklistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val checkBox: CheckBox = view.findViewById(R.id.item_checkbox)
        val editButton: ImageButton = view.findViewById(R.id.edit_button)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checklist, parent, false)
        return ChecklistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        val checklistItem = checklistItems[position]
        holder.title.text = checklistItem.title
        holder.checkBox.isChecked = checklistItem.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            checklistItem.isChecked = isChecked
        }

        holder.editButton.setOnClickListener {
            onItemEdit(position) // Edit the item
        }

        holder.deleteButton.setOnClickListener {
            onItemDelete(position) // Delete the item
        }
    }

    override fun getItemCount() = checklistItems.size
}
