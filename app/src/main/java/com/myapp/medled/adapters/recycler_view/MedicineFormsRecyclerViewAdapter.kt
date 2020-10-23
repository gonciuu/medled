package com.myapp.medled.adapters.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapp.medled.R
import com.myapp.medled.adapters.recycler_view.view_holders.MedicineFormsViewHolder
import com.myapp.medled.models.MedicineFormCard
import com.myapp.medled.screens.medicines.MedicineFormInterface

class MedicineFormsRecyclerViewAdapter(private val listOfMedicinesForms : ArrayList<MedicineFormCard>,private val listener:MedicineFormInterface):RecyclerView.Adapter<MedicineFormsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineFormsViewHolder {
        return MedicineFormsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_and_desc_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfMedicinesForms.size
    }

    override fun onBindViewHolder(holder: MedicineFormsViewHolder, position: Int) {
        //set title and medicine form photo
        holder.description.text = listOfMedicinesForms[holder.adapterPosition].title
        holder.photo.setImageResource(listOfMedicinesForms[holder.adapterPosition].photo)

        //set text color and background tint
        holder.allBox.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,if(listOfMedicinesForms[holder.adapterPosition].isChoose) R.color.colorPrimary else R.color.backgroundLightGray)
        holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context,if(listOfMedicinesForms[holder.adapterPosition].isChoose) R.color.white else R.color.darkGray))

        //handle on click on the box
        holder.allBox.setOnClickListener {
            listener.changeForm(listOfMedicinesForms[holder.adapterPosition])
            pillFormClick(holder.adapterPosition)
        }
    }

    //---------| clear all boxes and make choosen only clicked box |-----------
    private fun pillFormClick(index:Int){
        listOfMedicinesForms.forEach { it.isChoose = false }
        listOfMedicinesForms[index].isChoose = true
        notifyDataSetChanged()
    }
    //=========================================================================
}