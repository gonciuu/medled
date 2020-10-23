package com.myapp.medled.adapters.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.view_holders.PatientsRecyclerViewHolder
import com.myapp.medled.models.Request
import com.myapp.medled.screens.doctor.AllDoctorsInterface

class PatientsRecyclerViewAdapter(private val listOfPatients:ArrayList<Request>,private val listener:AllDoctorsInterface): RecyclerView.Adapter<PatientsRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsRecyclerViewHolder {
       return PatientsRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.patient_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfPatients.size
    }

    override fun onBindViewHolder(holder: PatientsRecyclerViewHolder, position: Int) {
        val request = listOfPatients[holder.adapterPosition]

        //setup patient data
        holder.patientName.text = request.patient!!.name
        holder.patientAvatar.setImageResource(request.patient.avatar)
        holder.patientBio.text = request.patient.bio

        //accept request
        holder.confirmButton.setOnClickListener {
            listener.onRequestAccept(request)
        }
    }
}