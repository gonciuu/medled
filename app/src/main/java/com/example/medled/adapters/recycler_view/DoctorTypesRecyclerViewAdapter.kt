package com.example.medled.adapters.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.view_holders.DoctorsTypesViewHolder
import com.example.medled.adapters.recycler_view.view_holders.MedicineFormsViewHolder
import com.example.medled.models.DoctorTypeCard

class DoctorTypesRecyclerViewAdapter(private val listOfDoctorsTypes: ArrayList<DoctorTypeCard>):RecyclerView.Adapter<DoctorsTypesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsTypesViewHolder {
        return DoctorsTypesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_and_desc_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfDoctorsTypes.size
    }

    override fun onBindViewHolder(holder: DoctorsTypesViewHolder, position: Int) {

        //---------------------------------| setup data |-----------------------------------
        holder.description.text = listOfDoctorsTypes[holder.adapterPosition].name
        holder.photo.setImageResource(listOfDoctorsTypes[holder.adapterPosition].photo)

        //------------------------| set colors of bg and text |--------------------------
        holder.allBox.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context,if(listOfDoctorsTypes[holder.adapterPosition].isChoose) R.color.colorPrimary else R.color.backgroundLightGray)
        holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context,if(listOfDoctorsTypes[holder.adapterPosition].isChoose) R.color.white else R.color.darkGray))

        //-------------------| set layout width and height |---------------------
        val params: ViewGroup.LayoutParams = holder.allBox.layoutParams
        params.width = 370
        params.height = 370
        holder.allBox.layoutParams = params
        //=======================================================================

        //click on card
        holder.allBox.setOnClickListener {
            doctorCardClick(holder.adapterPosition)
        }
    }

    //-----------------------| Click on card |---------------------------
    private fun doctorCardClick(index:Int){
        listOfDoctorsTypes.forEach { it.isChoose=false }
        listOfDoctorsTypes[index].isChoose = true
        notifyDataSetChanged()
    }
    //===================================================================


}