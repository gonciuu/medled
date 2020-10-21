package com.example.medled.screens.doctor

import com.example.medled.models.Request
import com.example.medled.models.User

interface AllDoctorsInterface {
    fun changeType(doctorType:String)

    fun chooseDoctor(doctor:User)

    fun onDoctorsDatabaseChanged(allDoctors: ArrayList<User>)

    fun onRequestsDatabaseChanged(allRequests: ArrayList<Request>)

    fun onRequestAccept(request: Request)

}