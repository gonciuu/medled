package com.myapp.medled.screens.doctor

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.myapp.medled.models.Request

class ConfirmDialog(private val title: String, private val message: String, private val isDoctor:Boolean,private val request: Request, private val listener: ChatInterface) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //show alert dialog with 2 options - cancel and delete
        return activity?.let {
            AlertDialog.Builder(it).setTitle(title).setMessage(message)
                .setPositiveButton(
                    "Ok"
                ) { dialog, _ ->
                    //doctor leave
                    if(isDoctor) listener.onDoctorLeave(request)
                    //user leave
                    else listener.onDeleteChat(request.id)
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