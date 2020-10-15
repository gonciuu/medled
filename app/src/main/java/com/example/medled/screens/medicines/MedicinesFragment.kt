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
import com.example.medled.models.CalendarDay
import kotlinx.android.synthetic.main.fragment_medicines.*
import java.util.*


class MedicinesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
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
        val listOfDaysLetters = arrayListOf<String>("S","M","T","W","T","F","S")
        val listOfNumbersTextViews = arrayListOf<TextView>(firstDay,secondDay,thirdDay,fourthDay,fifthDay,sixthDay,seventhDay)
        val listOfDaysLettersTextViews = arrayListOf<TextView>(firstDayLetter,secondDayLetter,thirdDayLetter,fourthDayLetter,fifthDayLetter,sixthDayLetter,seventhDayLetter)

        val listOfDays =  arrayListOf<CalendarDay>()
        val calendar = Calendar.getInstance()
        var currentTime = calendar.timeInMillis

        for(i in 0..6){
            Log.d("TAG",calendar.get(Calendar.DAY_OF_WEEK).toString())
            listOfDays.add(CalendarDay(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),listOfDaysLetters[calendar.get(Calendar.DAY_OF_WEEK)-1]))
            calendar.timeInMillis = currentTime + 86400000
            currentTime+=86400000
        }


        Log.d("TAG",listOfDays.toString())


        listOfNumbersTextViews.forEach { day ->
            day.text = listOfDays[listOfNumbersTextViews.indexOf(day)].day.toString()
            listOfDaysLettersTextViews[listOfNumbersTextViews.indexOf(day)].text = listOfDays[listOfNumbersTextViews.indexOf(day)].dayLetter
            day.setOnClickListener {
                //set all days white
                listOfNumbersTextViews.forEach { tv->
                    tv.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.backgroundLightGray)
                    tv.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                //set clicked day blue
                day.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary)
                day.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            }
        }


    }


    //==================================================================




}