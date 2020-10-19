package com.example.medled.models

data class User(
    val id: Int,
    var name: String,
    var bio: String,
    val avatar: Int,
    var isDoctor: Boolean,
    var medicineBranch: String?
)