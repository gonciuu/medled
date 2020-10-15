package com.example.medled.helpers

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import com.example.medled.R
import kotlinx.android.synthetic.main.fragment_register.*

class Helpers {

    fun getDialog(context: Context,title:String) : AlertDialog {
        return AlertDialog.Builder(context).setView(R.layout.progress_dialog_layout).setTitle(title).setCancelable(false).create()
    }
}