package com.example.medled.screens.medicines

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MedicinesRecyclerViewAdapter
import com.example.medled.databases.medicines_database.Medicine
import com.example.medled.databases.medicines_database.MedicinesViewModel
import com.example.medled.helpers.MedicinesCalendar
import com.example.medled.models.CalendarDay
import kotlinx.android.synthetic.main.fragment_medicines.*
import java.util.*
import kotlin.collections.ArrayList


class MedicinesFragment : Fragment(),DeleteMedicineInterface {

    private lateinit var medicinesViewModel: MedicinesViewModel
    private lateinit var allMedicines: List<Medicine>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medicines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicinesViewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(MedicinesViewModel::class.java)
        medicinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        setupNavigation()
        setCalendar()

        //------------------- get all medicines from database and set in into all medicines list --------------------
        medicinesViewModel.allMedicines.observe(viewLifecycleOwner, Observer {
            Log.d("TAG",it.toString())
            allMedicines = it
            setupRecyclerView(MedicinesCalendar().getFirstDay())
        })
        //============================================================================================================


    }


    private fun setupNavigation() = addMedicineButton.setOnClickListener {
        findNavController().navigate(R.id.action_medicinesFragment_to_addMedicineFragment)
    }

    //-------------------------| set adapter based on the choose day in calendar |--------------------------
    private fun setupRecyclerView(date: CalendarDay){
        val filterList = ArrayList<Medicine>()
        allMedicines.forEach { medicine->
            val medicineCalendar = Calendar.getInstance()
            medicineCalendar.timeInMillis = medicine.time
            val medicineDay = medicineCalendar.get(Calendar.DAY_OF_MONTH)
            val medicineMonth = medicineCalendar.get(Calendar.MONTH)

            //add to list olny this days which day and month is the same as choose date
            if(medicineDay==date.day && medicineMonth==date.month){
                filterList.add(medicine)
            }
        }

        Collections.sort(filterList,MedicinesArrayListComparator())
        medicinesRecyclerView.adapter = MedicinesRecyclerViewAdapter(filterList,this)
    }
    //======================================================================================================



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
                       listOfNumbersTextViews[i].backgroundTintList = ContextCompat.getColorStateList(requireActivity(), if(listOfCalendarDays[i].isChoose) R.color.colorPrimary else  R.color.backgroundLightGray)
                       listOfNumbersTextViews[i].setTextColor(ContextCompat.getColor(requireContext(),if(listOfCalendarDays[i].isChoose) R.color.white else R.color.black))
                }
                //Log.d("TAG",listOfCalendarDays[actualIndex].toString())
                setupRecyclerView(listOfCalendarDays[actualIndex])
            }
            //========================================
        }
    }

    //show the alert dialog to confirm medicine delete
    override fun showDeleteDialog(medicine: Medicine) {
        DeleteMedicineDialog(medicine,this).show(requireActivity().supportFragmentManager,"delete_medicine_dialog")
    }
    
    //delete medicine from the database
    override fun deleteMedicine(medicine: Medicine) {
        medicinesViewModel.deleteMedicine(medicine)
    }

    //==================================================================
}






