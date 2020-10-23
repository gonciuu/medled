package com.myapp.medled.screens.medicines.time_date_pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.myapp.medled.view_models.DateTimePickerViewModel
import java.util.*

class DatePickerHelper : DialogFragment(),DatePickerDialog.OnDateSetListener {


    //-------------------------------| show date picker dialog | ----------------------------------
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c :Calendar = Calendar.getInstance()
        return DatePickerDialog(requireActivity(),this,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH))
    }
    //============================================================================================

    //-------------------------------| handle set date and setup it into view model |----------------------------------
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val dateViewModel : DateTimePickerViewModel = ViewModelProvider(requireActivity()).get(DateTimePickerViewModel::class.java)
        val c = Calendar.getInstance()
        c.set(p1,p2,p3)
        dateViewModel.setDate(c.timeInMillis)
    }
    //=================================================================================================================


}