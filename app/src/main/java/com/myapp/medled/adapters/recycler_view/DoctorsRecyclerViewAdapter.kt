package com.myapp.medled.adapters.recycler_view

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.view_holders.DoctorsRecyclerViewHolder
import com.myapp.medled.models.User
import com.myapp.medled.screens.doctor.AllDoctorsInterface
import java.util.*
import kotlin.collections.ArrayList

class DoctorsRecyclerViewAdapter(private val listOfDoctors:ArrayList<User>, private val listener:AllDoctorsInterface) : RecyclerView.Adapter<DoctorsRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsRecyclerViewHolder {
        return DoctorsRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doctor_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfDoctors.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DoctorsRecyclerViewHolder, position: Int) {
        val doctor = listOfDoctors[holder.adapterPosition]

        //------------| get start and end time as string |----------------
        val c = Calendar.getInstance()
        c.timeInMillis = doctor.startTime!!
        val startTime = DateFormat.format("HH:mm",c).toString()
        c.timeInMillis = doctor.endTime!!
        val endTime = DateFormat.format("HH:mm",c).toString()
        //=================================================================

        //setup doctor's data
        holder.doctorName.text = doctor.name
        holder.doctorWorkTime.text = "$startTime-$endTime"
        holder.doctorStarCount.text = doctor.starCount.toString()
        holder.doctorType.text = doctor.medicineBranch
        holder.doctorAvatar.setImageResource(doctor.avatar)

        holder.allBox.setOnClickListener {
            listener.chooseDoctor(doctor)
        }

    }
}