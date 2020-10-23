package com.myapp.medled.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterTimeViewModel : ViewModel() {
    private val startTime = MutableLiveData<Long>()
    private val endTime = MutableLiveData<Long>()

    //work start time set
    fun setStartTime(chooseStartTime:Long){
        this.startTime.value = chooseStartTime
    }

    //work end time set
    fun setEndTime(chooseEndTime:Long){
        this.endTime.value = chooseEndTime
    }

    //get the work time
    fun getStartTime():LiveData<Long> = startTime
    fun getEndTime():LiveData<Long> = endTime
}