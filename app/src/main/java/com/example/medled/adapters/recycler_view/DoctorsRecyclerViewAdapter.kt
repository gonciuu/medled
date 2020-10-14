package com.example.medled.adapters.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.DoctorsRecyclerViewHolder

class DoctorsRecyclerViewAdapter : RecyclerView.Adapter<DoctorsRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsRecyclerViewHolder {
        return DoctorsRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doctor_card,parent,false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: DoctorsRecyclerViewHolder, position: Int) {
       // TODO("Not yet implemented")
    }
}