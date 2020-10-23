package com.myapp.medled.screens.doctor

import com.myapp.medled.models.Request
import com.myapp.medled.models.User

interface AllDoctorsInterface {
    fun changeType(doctorType:String)

    fun chooseDoctor(doctor:User)

    fun onDoctorsDatabaseChanged(allDoctors: ArrayList<User>)

    fun onRequestsDatabaseChanged(allRequests: ArrayList<Request>)

    fun onRequestAccept(request: Request)

}