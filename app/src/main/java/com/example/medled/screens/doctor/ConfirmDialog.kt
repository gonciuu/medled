package com.example.medled.screens.doctor

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.medled.databases.medicines_database.Medicine
import com.example.medled.models.Request
import com.example.medled.screens.medicines.DeleteMedicineInterface

class ConfirmDialog(private val title: String, private val message: String, private val isDoctor:Boolean,private val request: Request, private val listener: ChatInterface) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //show alert dialog with 2 options - cancel and delete
        return activity?.let {
            AlertDialog.Builder(it).setTitle(title).setMessage(message)
                .setPositiveButton(
                    "Ok"
                ) { dialog, _ ->
                    if(isDoctor) listener.onDoctorLeave(request)
                    else listener.onDeleteChat(request)
                    dialog.cancel()
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ ->
                    dialog.cancel()
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}