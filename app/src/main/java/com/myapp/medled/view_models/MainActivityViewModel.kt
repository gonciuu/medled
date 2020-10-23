package com.myapp.medled.view_models

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val isBottomNavVisible = MutableLiveData<Int>()
    val bottomNavVisibility : LiveData<Int> get() = isBottomNavVisible

    init {
        hideBottomNavigation()
    }

    //--------show bottom bar-----------
    fun showBottomNavigation() = isBottomNavVisible.postValue(View.VISIBLE)

    //-------hide bottom bar---------
    fun hideBottomNavigation() = isBottomNavVisible.postValue(View.GONE)

}