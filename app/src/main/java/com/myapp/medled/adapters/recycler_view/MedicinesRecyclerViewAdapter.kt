package com.myapp.medled.adapters.recycler_view

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.view_holders.MedicinesViewHolder
import com.myapp.medled.databases.medicines_database.Medicine
import com.myapp.medled.screens.medicines.DeleteMedicineInterface
import java.util.*


class MedicinesRecyclerViewAdapter (private val listOfMedicines: ArrayList<Medicine>,private val  listener: DeleteMedicineInterface) : RecyclerView.Adapter<MedicinesViewHolder>(){
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

        //set data in layout
        holder.medicineName.text = medicine.name
        holder.medicineTypeAndAmount.text = medicine.amount + " " + medicine.type + " " + medicine.formName
        holder.medicineTime.text = DateFormat.format("HH:mm", medicineTimeCalendar).toString()
        holder.medicineImage.setImageResource(medicine.formImage)

        //-------------------| Check if the pill time is gone |-------------------------
        if(medicine.time < Calendar.getInstance().timeInMillis){

            //set black-white image color
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)
            holder.medicineImage.colorFilter = ColorMatrixColorFilter(matrix)

            //strikethrough text
            val strike = Paint.STRIKE_THRU_TEXT_FLAG
            holder.medicineName.paintFlags = strike
            holder.medicineTypeAndAmount.paintFlags = strike
            holder.medicineTime.paintFlags = strike
        }
        //==============================================================================

        //-------------------| run delete function on long click on the medicine box |-----------------------
        holder.medicineBox.setOnLongClickListener {
            listener.showDeleteDialog(medicine)
            true
        }
        //===================================================================================================

    }

}