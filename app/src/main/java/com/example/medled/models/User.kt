package com.example.medled.models

data class User(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: Int,
    val isDoctor: Boolean,
    var medicineBranch: String?
)