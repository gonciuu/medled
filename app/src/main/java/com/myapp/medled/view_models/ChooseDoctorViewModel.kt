package com.myapp.medled.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.medled.models.User

class ChooseDoctorViewModel : ViewModel(){

    private var doctor = MutableLiveData<User>()

    fun setDoctor(chooseDoctor:User){
        this.doctor.value = chooseDoctor
    }

    fun getDoctor():LiveData<User> = doctor

}