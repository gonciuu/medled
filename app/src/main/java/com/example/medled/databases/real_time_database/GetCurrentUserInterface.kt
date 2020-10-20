package com.example.medled.databases.real_time_database

import com.example.medled.models.User

interface GetCurrentUserInterface {

    fun onGetCurrentUser(user: User)
}