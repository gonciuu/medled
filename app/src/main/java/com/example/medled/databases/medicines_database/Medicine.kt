package com.example.medled.databases.medicines_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    var name: String,
    var amount: String,
    var type: String,
    var time: Long,
    var duration: Int,
    var form: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}