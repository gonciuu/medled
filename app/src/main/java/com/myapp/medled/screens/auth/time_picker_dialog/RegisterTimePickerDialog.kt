package com.myapp.medled.screens.auth.time_picker_dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.myapp.medled.view_models.RegisterTimeViewModel
import java.util.*

class RegisterTimePickerDialog(private val isStartTime: Boolean) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    //-------------------------------| show time picker dialog | ----------------------------------
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(requireActivity(), this, 8, 0, DateFormat.is24HourFormat(context))
    }
    //=============================================================================================

    //-------------------------------| handle set time and setup it into view model |----------------------------------
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val timeViewModel: RegisterTimeViewModel = ViewModelProvider(requireActivity()).get(RegisterTimeViewModel::class.java)
        val c = Calendar.getInstance()
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), p1, p2,0)
        if (isStartTime) timeViewModel.setStartTime(c.timeInMillis)
        else timeViewModel.setEndTime(c.timeInMillis)
    }
    //=================================================================================================================


}
