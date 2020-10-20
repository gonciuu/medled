package com.example.medled.databases.real_time_database

import android.view.View
import android.widget.Toast
import com.example.medled.adapters.recycler_view.DoctorsRecyclerViewAdapter
import com.example.medled.helpers.Helpers
import com.example.medled.models.User
import com.example.medled.screens.doctor.AllDoctorsInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_all_doctors.*

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

    //-----------------------| Get all active doctors from database|-------------------------------
    fun getActiveDoctors(view: View,listener: AllDoctorsInterface){
        val arrayListOfDoctors = arrayListOf<User>()

        doctorsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
               Helpers().showSnackBar(p0.message,view)
            }
            override fun onDataChange(p0: DataSnapshot) {
                arrayListOfDoctors.clear()
                for(i in p0.children){
                    val doctor = i.getValue(User::class.java)!!
                    arrayListOfDoctors.add(doctor)
                }
                listener.onDoctorsDatabaseChanged(arrayListOfDoctors)
            }
        })
    }
    //===========================================================================================


    //-------------------------| Get current user based od uid |-------------------------

    fun getUserById(view: View,id:String,listener: GetCurrentUserInterface){
        var currentUser:User? = null
        doctorsRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: com.google.firebase.database.DatabaseError) {
                Helpers().showSnackBar(p0.message,view)
            }
            override fun onDataChange(p0: DataSnapshot) {
                for(i in p0.children){
                    val user = i.getValue(User::class.java)
                    if(user!!.id == id){
                        currentUser = user
                        break
                    }
                }
                listener.onGetCurrentUser(currentUser!!)
            }
        })
    }

    //====================================================================================
}