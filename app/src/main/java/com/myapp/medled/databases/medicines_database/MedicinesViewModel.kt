package com.myapp.medled.databases.medicines_database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicinesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicinesRepository = MedicinesRepository(application)
    val allMedicines: LiveData<List<Medicine>>

    init {
        allMedicines = repository.allMedicines
    }

    fun insertMedicine(medicine: Medicine) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMedicine(medicine)
        }
    }

    fun updateMedicine(medicine: Medicine) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMedicine(medicine)
        }
    }

    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMedicine(medicine)
        }
    }

}