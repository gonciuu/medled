package com.myapp.medled.adapters.recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.medicine_card.view.*

class MedicinesViewHolder(v:View): RecyclerView.ViewHolder(v){
    val medicineName: TextView = v.medicineName
    val medicineTypeAndAmount: TextView = v.medicineTypeAndAmount
    val medicineTime: TextView = v.medicineTime
    val medicineImage: ImageView = v.medicineImage
    val medicineBox: LinearLayout = v.medicineBox
}