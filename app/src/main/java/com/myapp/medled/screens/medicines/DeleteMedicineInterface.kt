package com.myapp.medled.screens.medicines

import com.myapp.medled.databases.medicines_database.Medicine

interface DeleteMedicineInterface {

    fun showDeleteDialog(medicine:Medicine)

    fun deleteMedicine(medicine:Medicine)

}