package com.example.medled.models

data class User(
    var id: String,
    var name: String,
    var bio: String,
    var avatar: Int,
    var isDoctor: Boolean,
    var medicineBranch: String?
)