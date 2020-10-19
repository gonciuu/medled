package com.example.medled.databases.real_time_database

import androidx.constraintlayout.solver.widgets.Helper
import com.example.medled.helpers.Helpers
import com.example.medled.models.User
import com.google.firebase.database.FirebaseDatabase

class RealTimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val patientsRef = database.getReference("Patients")
    private val doctorsRef = database.getReference("Doctors")


    fun insertUserToDatabase(user: User){
        if(user.isDoctor)
            doctorsRef.child(user.id.toString()).setValue(user)
        else{
            user.medicineBranch = null
            patientsRef.child(user.id.toString()).setValue(user)
        }
    }
}