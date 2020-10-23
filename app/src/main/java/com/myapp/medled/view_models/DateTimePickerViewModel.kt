package com.myapp.medled.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DateTimePickerViewModel : ViewModel() {

    private val date = MutableLiveData<Long>()
    private val time = MutableLiveData<Long>()

    fun setDate(setDate:Long){
        this.date.value = setDate
    }

    fun getDate():LiveData<Long> = date

    fun setTime(setTime:Long) {
        this.time.value = setTime
    }

    fun getTime():LiveData<Long> = time


}