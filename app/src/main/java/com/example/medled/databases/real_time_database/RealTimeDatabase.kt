package com.example.medled.databases.real_time_database

import android.view.View
import com.example.medled.models.User
import com.google.firebase.database.FirebaseDatabase

class RealTimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val patientsRef = database.getReference("Patients")
    private val doctorsRef = database.getReference("Doctors")


    //-------------------------| Insert user to realtime database |--------------------------
    fun insertUserToDatabase(user: User,view: View, errorListener: DatabaseError){
        if(user.isDoctor)
            //user is register as doctor
            doctorsRef.child(user.id.toString()).setValue(user).addOnFailureListener {
                //handle eventual error
                errorListener.errorHandled(it.message.toString(),view)
            }
        else{
            //user is register as patient
            user.medicineBranch = null
            user.starCount = null
            user.startTime = null
            user.endTime = null
            patientsRef.child(user.id.toString()).setValue(user).addOnFailureListener {
                //handle eventual error
                errorListener.errorHandled(it.message.toString(),view)
            }
        }
    }
    //========================================================================================
}