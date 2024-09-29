package com.example.miniproject1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment

class DialogFilterOptions : DialogFragment() {

    interface FilterOptionListener {
        fun onFilterSelected(selectedItems: List<String>)
    }

    private var listener: FilterOptionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_dialog_filter_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize all the checkboxes
        val checkFood = view.findViewById<CheckBox>(R.id.check_food)
        val checkHangout = view.findViewById<CheckBox>(R.id.check_hangout)
        val checkMonuments = view.findViewById<CheckBox>(R.id.check_monuments)
        val checkBusStop = view.findViewById<CheckBox>(R.id.check_bus_stop)
        val checkShopping = view.findViewById<CheckBox>(R.id.check_shopping)
        val checkPetrol = view.findViewById<CheckBox>(R.id.check_petrol)
        val checkATM = view.findViewById<CheckBox>(R.id.check_atm)
        val checkHospital = view.findViewById<CheckBox>(R.id.check_hospital)

        val selectButton = view.findViewById<Button>(R.id.btn_select)

        // Set click listener for the select button
        selectButton.setOnClickListener {
            val selectedItems = mutableListOf<String>()

            // Add selected items to the list
            if (checkFood.isChecked) selectedItems.add("Food")
            if (checkHangout.isChecked) selectedItems.add("Hangout Spots")
            if (checkMonuments.isChecked) selectedItems.add("Monuments")
            if (checkBusStop.isChecked) selectedItems.add("Bus Stop")
            if (checkShopping.isChecked) selectedItems.add("Shopping")
            if (checkPetrol.isChecked) selectedItems.add("Petrol")
            if (checkATM.isChecked) selectedItems.add("ATM")
            if (checkHospital.isChecked) selectedItems.add("Hospital")

            // Send selected items back to the activity or fragment
            listener?.onFilterSelected(selectedItems)
            dismiss()
        }
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is FilterOptionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FilterOptionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
