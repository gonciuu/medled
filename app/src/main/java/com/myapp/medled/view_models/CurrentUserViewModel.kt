package com.myapp.medled.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.medled.models.User

class CurrentUserViewModel:ViewModel() {

    private val currentUserData = MutableLiveData<User?>()
    //on class init
    init {
        setUser(null)
    }

    fun setUser(currentUser:User?){
        this.currentUserData.value = currentUser
    }

    fun getUser():LiveData<User?> = currentUserData

}