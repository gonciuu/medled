package com.example.medled.databases.real_time_database

import android.view.View
import com.example.medled.models.User
import com.google.firebase.database.FirebaseDatabase

class RealTimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val patientsRef = database.getReference("Patients")
    private val doctorsRef = database.getReference("Doctors")


    fun insertUserToDatabase(user: User,view: View, errorListener: DatabaseError){
        if(user.isDoctor)
            doctorsRef.child(user.id.toString()).setValue(user).addOnFailureListener {
                errorListener.errorHandled(it.message.toString(),view)
            }
        else{
            user.medicineBranch = null
            patientsRef.child(user.id.toString()).setValue(user).addOnFailureListener {
                errorListener.errorHandled(it.message.toString(),view)
            }
        }
    }
}