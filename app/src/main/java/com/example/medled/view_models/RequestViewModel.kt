package com.example.medled.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medled.models.Request

class RequestViewModel : ViewModel(){

    private val requestId = MutableLiveData<String>()

    fun setRequest(mRequestId:String){
        requestId.value = mRequestId
    }

    fun getRequest(): LiveData<String> = requestId

}