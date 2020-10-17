package com.example.medled.adapters.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.MedicinesViewHolder
import com.example.medled.databases.medicines_database.Medicine


class MedicinesRecyclerViewAdapter (private val listOfMedicines: ArrayList<Medicine>) : RecyclerView.Adapter<MedicinesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinesViewHolder {
        return  MedicinesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.medicine_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfMedicines.size
    }

    override fun onBindViewHolder(holder: MedicinesViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

}