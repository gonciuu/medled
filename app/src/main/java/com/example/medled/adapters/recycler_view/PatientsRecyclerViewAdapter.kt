package com.example.medled.adapters.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.PatientsRecyclerViewHolder
import com.example.medled.models.User
import com.example.medled.screens.doctor.AllDoctorsInterface

class PatientsRecyclerViewAdapter(private val listOfPatients:ArrayList<User>,private val listener:AllDoctorsInterface): RecyclerView.Adapter<PatientsRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsRecyclerViewHolder {
       return PatientsRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.patient_card,parent,false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: PatientsRecyclerViewHolder, position: Int) {

    }
}