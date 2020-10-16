package com.example.medled.screens.medicines

import android.app.Activity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medled.R
import com.example.medled.adapters.recycler_view.MedicineFormsRecyclerViewAdapter
import com.example.medled.helpers.Helpers
import com.example.medled.models.MedicineFormCard
import com.example.medled.screens.medicines.time_date_pickers.DatePickerHelper
import com.example.medled.screens.medicines.time_date_pickers.TimePickerHelper
import com.example.medled.view_models.DateTimePickerViewModel
import kotlinx.android.synthetic.main.fragment_add_medicine.*
import java.util.*


class AddMedicineFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        setupRecyclerView()
        onSeekbarChanged()
        setupMedicineType()
        setupDateAndTimePicker()
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
        medicineFormRecyclerView.adapter = MedicineFormsRecyclerViewAdapter(listOfMedicinesForms){setPillForm()}
    }
    //=============================================================================================================================

    //----------------------------------| Set pill form in the medicine object |-----------------------------------------
    private fun setPillForm(){
        Toast.makeText(requireContext(),"ZMIENIONO FORME",Toast.LENGTH_SHORT).show()
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
        medicineTypeChooser.keyListener = null;
        medicineTypeChooser.setOnClickListener {
            medicineTypeChooser.showDropDown()
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
            //tu bedzie ustawienie czasu w obiekcie
            c.timeInMillis = time
            timeTextInput.setText(DateFormat.format("HH:mm", c).toString())
        })
        //=========================================================================================================================

        //---------------------------------observing change date in datepicker  (viewmodel)----------------------------------------------
        dateTimePickerViewModel.getDate().observe(viewLifecycleOwner, Observer { date->
            //tu bedzie ustawienie daty w obiekcie
            c.timeInMillis = date
            dateTextInput.setText(DateFormat.format("dd MMMM yyyy", c).toString())
        })
        //=========================================================================================================================
    }
    //===============================================================================================

}