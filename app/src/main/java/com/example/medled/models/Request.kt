package com.example.medled.models

data class Request (val id:String = "", val patient:User? = null, val doctor: User? = null, val isUserActive: Boolean = false, val isDoctorActive: Boolean = false/*,val messages:ArrayList<Message> = arrayListOf()*/)