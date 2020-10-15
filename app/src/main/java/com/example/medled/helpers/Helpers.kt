package com.example.medled.helpers

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.example.medled.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*

class Helpers {

    fun getLoadingDialog(context: Context, title: String): AlertDialog {
        return AlertDialog.Builder(context).setView(R.layout.progress_dialog_layout).setTitle(title)
            .setCancelable(false).create()
    }

    fun showSnackBar(message:String, view: View) = Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()


    //-------------------| Do an action on soft keyboard enter button pressed |-----------------------
    fun keyboardEnterButtonClick(editText: EditText, action: () -> Unit){
        editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                action()
            }
            false
        }
    }
    //================================================================================================
}