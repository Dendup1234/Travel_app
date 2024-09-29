package com.example.miniproject1

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChecklistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: ImageButton
    private lateinit var checklistAdapter: ChecklistAdapter
    private val databaseHelper by lazy { ChecklistDatabaseHelper(this) }
    private var checklistItems: MutableList<ChecklistItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        checklistAdapter = ChecklistAdapter(checklistItems, ::editItem, ::deleteItem)
        recyclerView.adapter = checklistAdapter

        // Load saved checklist items from the database
        loadChecklist()

        // Add new item button listener
        addButton.setOnClickListener {
            showAddItemDialog()
        }
    }

    // Function to add a new checklist item
    private fun showAddItemDialog() {
        val input = EditText(this).apply {
            hint = "Enter item title"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        AlertDialog.Builder(this)
            .setTitle("Add New Item")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val itemTitle = input.text.toString()
                if (itemTitle.isNotEmpty()) {
                    val newItem = ChecklistItem(title = itemTitle)
                    databaseHelper.addChecklistItem(newItem)
                    checklistItems.add(newItem)
                    checklistAdapter.notifyItemInserted(checklistItems.size - 1)
                    Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Function to edit an existing checklist item
    private fun editItem(position: Int) {
        val item = checklistItems[position]
        val input = EditText(this).apply {
            hint = "Edit item title"
            inputType = InputType.TYPE_CLASS_TEXT
            setText(item.title)
        }

        AlertDialog.Builder(this)
            .setTitle("Edit Item")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val updatedTitle = input.text.toString()
                if (updatedTitle.isNotEmpty()) {
                    item.title = updatedTitle
                    databaseHelper.updateChecklistItem(item)
                    checklistAdapter.notifyItemChanged(position)
                    Toast.makeText(this, "Item updated successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Function to delete an existing checklist item
    private fun deleteItem(position: Int) {
        val itemId = checklistItems[position].id
        AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { _, _ ->
                databaseHelper.deleteChecklistItem(itemId)
                checklistItems.removeAt(position)
                checklistAdapter.notifyItemRemoved(position)
                Toast.makeText(this, "Item deleted successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Load the checklist from the database
    private fun loadChecklist() {
        checklistItems.clear()
        checklistItems.addAll(databaseHelper.getAllChecklistItems())
        checklistAdapter.notifyDataSetChanged()
    }
}
