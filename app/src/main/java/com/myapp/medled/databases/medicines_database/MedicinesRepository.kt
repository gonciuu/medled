package com.myapp.medled.databases.medicines_database

import android.app.Application

class MedicinesRepository(application: Application) {

    private val medicinesDao:MedicineDao

    init {
        val database = MedicinesDatabase.getDatabase(application.applicationContext)
        this.medicinesDao = database!!.medicineDao()
    }

    suspend fun insertMedicine(medicine: Medicine){
        medicinesDao.insertMedicine(medicine)
    }

    suspend fun updateMedicine(medicine: Medicine){
        medicinesDao.updateMedicine(medicine)
    }

    suspend fun deleteMedicine(medicine: Medicine){
        medicinesDao.deleteMedicine(medicine)
    }

    val allMedicines = medicinesDao.getAllMedicines()

}