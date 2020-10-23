package com.myapp.medled.databases.medicines_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class], version = 6)
abstract class MedicinesDatabase : RoomDatabase() {

    abstract fun medicineDao(): MedicineDao

    companion object {
        private var instance: MedicinesDatabase? = null

        fun getDatabase(context: Context): MedicinesDatabase? {
            if (instance == null) instance = synchronized(this) {
                Room.databaseBuilder(context, MedicinesDatabase::class.java, "medicines").build()
            }
            return instance
        }
    }

}

