package com.example.medled.adapters.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.MedicineFormsViewHolder
import com.example.medled.models.MedicineFormCard

class MedicineFormsRecyclerViewAdapter(private val listOfMedicinesForms : ArrayList<MedicineFormCard>,private val setPillForm : ()-> Unit):RecyclerView.Adapter<MedicineFormsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineFormsViewHolder {
        return MedicineFormsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_and_desc_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfMedicinesForms.size
    }

    override fun onBindViewHolder(holder: MedicineFormsViewHolder, position: Int) {
        holder.description.text = listOfMedicinesForms[holder.adapterPosition].title
        holder.photo.setImageResource(listOfMedicinesForms[holder.adapterPosition].photo)
        holder.allBox.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,if(listOfMedicinesForms[holder.adapterPosition].isChoose) R.color.colorPrimary else R.color.backgroundLightGray)
        holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context,if(listOfMedicinesForms[holder.adapterPosition].isChoose) R.color.white else R.color.darkGray))
        holder.allBox.setOnClickListener {
            pillFormClick(holder.adapterPosition)
            setPillForm()
        }
    }

    private fun pillFormClick(index:Int){
        listOfMedicinesForms.forEach { it.isChoose = false }
        listOfMedicinesForms[index].isChoose = true
        notifyDataSetChanged()
    }
}