package com.example.medled.screens.medicines

import android.app.Activity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.Helper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MedicineFormsRecyclerViewAdapter
import com.example.medled.databases.medicines_database.Medicine
import com.example.medled.databases.medicines_database.MedicinesViewModel
import com.example.medled.helpers.Helpers
import com.example.medled.models.MedicineFormCard
import com.example.medled.screens.medicines.time_date_pickers.DatePickerHelper
import com.example.medled.screens.medicines.time_date_pickers.TimePickerHelper
import com.example.medled.view_models.DateTimePickerViewModel
import kotlinx.android.synthetic.main.fragment_add_medicine.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.hours


class AddMedicineFragment : Fragment(),MedicineFormInterface {


    private lateinit var medicine:Medicine
    private lateinit var medicinesViewModel : MedicinesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicinesViewModel =  ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(MedicinesViewModel::class.java)
        medicine =  Medicine("","3","pills",Calendar.getInstance().timeInMillis,3,"Tablet")

        setupNavigation()
        setupRecyclerView()
        onSeekbarChanged()
        setupMedicineType()
        setupDateAndTimePicker()

        saveMedicineButton.setOnClickListener {
            insertMedicine()
        }

    }

    private fun setupNavigation() =  addMedicineBackButton.setOnClickListener {
        requireActivity().onBackPressed()
    }

    //-------------------------------------| setup recycler view with medicines forms |--------------------------------------------
    private fun setupRecyclerView(){
        val listOfMedicinesForms = arrayListOf<MedicineFormCard>(
            MedicineFormCard("SIEMA",R.drawable.doctor_avatar_1,true),
            MedicineFormCard("SIEMA2",R.drawable.doctor_avatar_1,false),
            MedicineFormCard("SIEMA3",R.drawable.doctor_avatar_1,false),
            MedicineFormCard("SIEMA4",R.drawable.doctor_avatar_1,false)
        )

        medicineFormRecyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        medicineFormRecyclerView.adapter = MedicineFormsRecyclerViewAdapter(listOfMedicinesForms,this)
    }
    //=============================================================================================================================

    //----------------------------------| Set pill form in the medicine object |-----------------------------------------
    override fun changeForm(form: String) {
        medicine.form = form
    }
    //===================================================================================================================

    //-------------------------------| set count of weeks on seekbar changed |-----------------------------
    private fun onSeekbarChanged(){
        durationSeekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                durationText.text = resources.getQuantityString(R.plurals.numberOfSongsAvailable,p1,p1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }
    //======================================================================================================

    //----------------------------| setup medicine type auto complete text view as spinner |------------------------------
    private fun setupMedicineType(){
        medicineTypeChooser.setAdapter(ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,arrayListOf("pills", "ml", "mg")))
        medicineTypeChooser.inputType = 0
        medicineTypeChooser.keyListener = null
        medicineTypeChooser.setOnClickListener {
            medicineTypeChooser.showDropDown()
        }

        //set type of medicine object
        medicineTypeChooser.setOnItemClickListener { _, _, i, _ ->
            medicine.type = medicineTypeChooser.adapter.getItem(i).toString()
        }

        //close keyboard on amount enter click
        val helper: Helpers = Helpers()
        helper.keyboardEnterButtonClick(amountInputField) {closeKeyboard()}
    }
    //=====================================================================================================================

    //--------------------------------| Close the keyboard |-------------------------------------
    private fun closeKeyboard(){
        val imm: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    //============================================================================================

    //------------------------------| Setup time and date picker |-----------------------------------

    private fun setupDateAndTimePicker(){

        //show time picker
        (arrayListOf<View>(chooseTimeButton,timeTextInput)).forEach {
            it.setOnClickListener {
                val timePickerDialog: TimePickerHelper = TimePickerHelper()
                timePickerDialog.show(requireActivity().supportFragmentManager, "time_picker")
            }
        }

        //show date picker
        (arrayListOf<View>(chooseDateButton,dateTextInput)).forEach {
            it.setOnClickListener {
                val datePickerDialog: DatePickerHelper = DatePickerHelper()
                datePickerDialog.show(requireActivity().supportFragmentManager, "date_picker")
            }
        }

        val dateTimePickerViewModel : DateTimePickerViewModel = ViewModelProvider(requireActivity()).get(DateTimePickerViewModel::class.java)
        val c = Calendar.getInstance()
        //------set actual time in textviews-------
        dateTimePickerViewModel.setDate(c.timeInMillis)
        dateTimePickerViewModel.setTime(c.timeInMillis)
        //=========================================

        //--------------------------------observing change time in timepicker (viewmodel)----------------------------------------------
        dateTimePickerViewModel.getTime().observe(viewLifecycleOwner, Observer { time->
            val helper = Calendar.getInstance()
            helper.timeInMillis = time

            //set madicine time
            c.set(Calendar.HOUR,helper.get(Calendar.HOUR))
            c.set(Calendar.MINUTE,helper.get(Calendar.MINUTE))

            medicine.time = c.timeInMillis
            timeTextInput.text = DateFormat.format("HH:mm", helper).toString()
        })
        //=========================================================================================================================

        //---------------------------------observing change date in datepicker  (viewmodel)----------------------------------------------
        dateTimePickerViewModel.getDate().observe(viewLifecycleOwner, Observer { date->
            val helper = Calendar.getInstance()
            helper.timeInMillis = date

            //set madicine date
            c.set(Calendar.YEAR,helper.get(Calendar.YEAR))
            c.set(Calendar.MONTH,helper.get(Calendar.MONTH))
            c.set(Calendar.DAY_OF_MONTH,helper.get(Calendar.DAY_OF_MONTH))

            medicine.time = c.timeInMillis
            dateTextInput.text = DateFormat.format("dd MMMM yyyy", helper).toString()
        })
        //=========================================================================================================================
    }
    //===============================================================================================



    //-------------------------------| Insert medicine to database |---------------------------------
    private fun insertMedicine(){
        val helpers:Helpers = Helpers()

        //---------set medicine property by text in input field-------------
        medicine.name = if(medicineNameInput.text.isNullOrEmpty()) "Medicine" else medicineNameInput.text.toString()
        medicine.amount = if(amountInputField.text.isNullOrEmpty()) "Blank" else amountInputField.text.toString()
        medicine.duration = durationSeekbar.progress
        //==================================================================

        try{
            //---------handle if the setdate is lower than actual date-----------
            if(medicine.time + 100000 > System.currentTimeMillis()){
                helpers.showSnackBar("Saved",requireView())
                medicinesViewModel.insertMedicine(medicine)
                requireActivity().onBackPressed()
            }
            else helpers.showSnackBar("Cannot save medicine which date has already passed",requireView())
        }catch (ex:Exception){
            helpers.showSnackBar(ex.message.toString(),requireView())
        }
        
    }


    //===============================================================================================

}