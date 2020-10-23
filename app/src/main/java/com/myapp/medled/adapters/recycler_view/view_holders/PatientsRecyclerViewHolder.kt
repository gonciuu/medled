package com.myapp.medled.adapters.recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.patient_card.view.*

class PatientsRecyclerViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val patientName: TextView = v.patientName
    val patientAvatar: ImageView = v.patientAvatar
    val patientBio: TextView = v.patientBio
    val confirmButton: ImageView = v.confirmButton
}