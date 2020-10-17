package com.example.medled.screens.medicines

import com.example.medled.databases.medicines_database.Medicine

interface DeleteMedicineInterface {

    fun showDeleteDialog(medicine:Medicine)

    fun deleteMedicine(medicine:Medicine)

}