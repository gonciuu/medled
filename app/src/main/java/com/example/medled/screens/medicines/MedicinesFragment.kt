package com.example.medled.screens.medicines

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_medicines.*


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
        val listOfNumbers = arrayListOf<TextView>(firstDay,secondDay,thirdDay,fourthDay,fifthDay,sixthDay,seventhDay)
        listOfNumbers.forEach { day ->
            day.setOnClickListener {
                //set all days white
                listOfNumbers.forEach { tv->
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