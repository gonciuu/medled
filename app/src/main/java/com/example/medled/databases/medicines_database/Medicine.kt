package com.example.medled.databases.medicines_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    val name: String,
    val amount: String,
    val type: String,
    val time: Long,
    val duration: Int,
    val form: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}