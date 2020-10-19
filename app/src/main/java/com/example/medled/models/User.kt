package com.example.medled.models

import com.example.medled.R

data class User(
    var id: String = "",
    var name: String = "",
    var bio: String = "",
    var avatar: Int = R.drawable.doctor_avatar_1,
    var isDoctor: Boolean = true,
    var medicineBranch: String? = null,
    var startTime: Long? = null,
    var endTime: Long? = null,
    var starCount: Float? = null
)