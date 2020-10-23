package com.myapp.medled.databases.real_time_database

import com.myapp.medled.models.User

interface GetCurrentUserInterface {

    fun onGetCurrentUser(user: User)
}