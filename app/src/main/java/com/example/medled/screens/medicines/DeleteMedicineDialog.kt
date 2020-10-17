package com.example.medled.screens.medicines

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.medled.databases.medicines_database.Medicine

class DeleteMedicineDialog(private val medicine: Medicine,private val listener:DeleteMedicine) : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).setMessage("Are you sure to delete this medicine ?")
                .setPositiveButton("Delete"
                ) { dialog, _ ->
                    listener.deleteMedicine(medicine)
                    dialog.cancel()
                }
                .setNegativeButton("Cancel"
                ) { dialog, _ ->
                    dialog.cancel()
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}