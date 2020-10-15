package com.example.medled.screens.medicines

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MedicinesRecyclerViewAdapter
import com.example.medled.helpers.MedicinesCalendar
import com.example.medled.models.CalendarDay
import kotlinx.android.synthetic.main.fragment_medicines.*
import java.util.*
import kotlin.collections.ArrayList


class MedicinesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupRecyclerView()
        setCalendar()
    }



    private fun setupNavigation() = addMedicineButton.setOnClickListener {
        findNavController().navigate(R.id.action_medicinesFragment_to_addMedicineFragment)
    }

    private fun setupRecyclerView(){
        medicinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        medicinesRecyclerView.adapter = MedicinesRecyclerViewAdapter()
    }



    //----------------------| Setup calendar |--------------------------
    private fun setCalendar(){

        //--------------| textvies from calendar layout view |--------------------
        val listOfNumbersTextViews = arrayListOf<TextView>(firstDay,secondDay,thirdDay,fourthDay,fifthDay,sixthDay,seventhDay)
        val listOfDaysLettersTextViews = arrayListOf<TextView>(firstDayLetter,secondDayLetter,thirdDayLetter,fourthDayLetter,fifthDayLetter,sixthDayLetter,seventhDayLetter)
        //=========================================================================

        //the weekdays of the nearest week
        val listOfCalendarDays :ArrayList<CalendarDay> = MedicinesCalendar().getListOfDays()

        listOfNumbersTextViews.forEach { day ->
            //-----------------set data in textviews---------------------
            val actualIndex:Int = listOfNumbersTextViews.indexOf(day)
            day.text = listOfCalendarDays[actualIndex].day.toString()
            listOfDaysLettersTextViews[actualIndex].text = listOfCalendarDays[actualIndex].dayLetter
            //===========================================================

            //-----------handle day click--------------
            day.setOnClickListener {
                //set all days choose to false
                listOfCalendarDays.forEach { it.isChoose = false }
                //set clicked day choose to true
                listOfCalendarDays[actualIndex].isChoose = true
                for(i in 0 until listOfNumbersTextViews.size) {
                    //set colors based on choose property
                       listOfNumbersTextViews[i].backgroundTintList =  if(listOfCalendarDays[i].isChoose) ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary) else ContextCompat.getColorStateList(requireActivity(), R.color.backgroundLightGray)
                       listOfNumbersTextViews[i].setTextColor(if(listOfCalendarDays[i].isChoose) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))
                }
                //Log.d("TAG",listOfCalendarDays[actualIndex].toString())
                //tu bedzie kod to ustawienia recyclerviewa
            }
            //========================================
        }
    }
}


    //==================================================================



