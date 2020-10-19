package com.example.medled.databases.real_time_database

import com.example.medled.models.User
import com.google.firebase.database.FirebaseDatabase

class RealTimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val patientsRef = database.getReference("Users")
    private val doctorsRef = database.getReference("Users")



    fun insertUserToDatabase(user: User){
        if(user.isDoctor)
            doctorsRef.setValue(user)
        else{
            user.medicineBranch = null
            patientsRef.setValue(user)
        }

    }
}