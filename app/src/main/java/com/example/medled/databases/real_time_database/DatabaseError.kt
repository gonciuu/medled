package com.example.medled.databases.real_time_database

import android.view.View

interface DatabaseError {
    fun errorHandled(errorMessage:String,view: View)
}