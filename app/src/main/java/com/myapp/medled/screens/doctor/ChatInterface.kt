package com.myapp.medled.screens.doctor

import com.myapp.medled.models.Request

interface ChatInterface {

    fun onRequestChanged(request: Request)

    fun onDeleteChat(requestId: String)

    fun onDoctorLeave(request: Request)

    fun onPatientDisabledChat()

}