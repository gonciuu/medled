package com.example.medled.screens.doctor

import com.example.medled.models.Request

interface ChatInterface {

    fun onRequestChanged(request: Request)

    fun onDeleteChat(requestId: String)

    fun onDoctorLeave(request: Request)

    fun onPatientDisabledChat()

}