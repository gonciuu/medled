package com.example.medled.screens.medicines.time_date_pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class DatePickerHelper : DialogFragment(),DatePickerDialog.OnDateSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c :Calendar = Calendar.getInstance()
        return DatePickerDialog(requireActivity(),this,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH))
    }
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
    }


}