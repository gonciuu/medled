package com.myapp.medled.adapters.recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.doctor_card.view.*

class DoctorsRecyclerViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val doctorName: TextView = v.doctorName
    val doctorAvatar: ImageView = v.doctorAvatar
    val doctorType: TextView = v.doctorType
    val doctorStarCount: TextView = v.doctorStarCount
    val doctorWorkTime: TextView = v.doctorWorkTime
    val allBox: LinearLayout = v.allDoctorBox

}