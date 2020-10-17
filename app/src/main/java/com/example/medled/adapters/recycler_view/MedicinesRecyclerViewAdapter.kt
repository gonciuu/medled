package com.example.medled.adapters.recycler_view

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.MedicinesViewHolder
import com.example.medled.databases.medicines_database.Medicine
import java.util.*
import kotlin.collections.ArrayList


class MedicinesRecyclerViewAdapter (private val listOfMedicines: ArrayList<Medicine>) : RecyclerView.Adapter<MedicinesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinesViewHolder {
        return  MedicinesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.medicine_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfMedicines.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MedicinesViewHolder, position: Int) {
        val medicine = listOfMedicines[holder.adapterPosition]
        val medicineTimeCalendar = Calendar.getInstance()
        medicineTimeCalendar.timeInMillis = medicine.time

        holder.medicineName.text = medicine.name
        holder.medicineTypeAndAmount.text = medicine.amount + " " + medicine.type + " " + medicine.form
        holder.medicineTime.text = DateFormat.format("HH:mm", medicineTimeCalendar).toString()
        
    }

}