package com.example.medled.adapters.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.MedicineFormsViewHolder
import com.example.medled.models.MedicineFormCard

class DoctorTypesRecyclerViewAdapter():RecyclerView.Adapter<MedicineFormsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineFormsViewHolder {
        return MedicineFormsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_and_desc_card,parent,false))
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: MedicineFormsViewHolder, position: Int) {

    }


}