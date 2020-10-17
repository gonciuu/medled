package com.example.medled.screens.medicines

import com.example.medled.databases.medicines_database.Medicine

class MedicinesArrayListComparator:Comparator<Medicine> {
    override fun compare(p0: Medicine?, p1: Medicine?): Int {
        return p0!!.time.compareTo(p1!!.time)
    }
}