package com.example.medled.helpers

import com.example.medled.models.CalendarDay
import java.util.*
import kotlin.collections.ArrayList

class MedicinesCalendar {


    fun getListOfDays() : ArrayList<CalendarDay>{
        val listOfDaysLetters :ArrayList<String> = arrayListOf<String>("S","M","T","W","T","F","S")
        val listOfDays :ArrayList<CalendarDay> = arrayListOf<CalendarDay>()
        val calendar :Calendar = Calendar.getInstance()
        var currentTime :Long = calendar.timeInMillis

        for (i in 0..6) {
            listOfDays.add(
                CalendarDay(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH),
                    listOfDaysLetters[calendar.get(
                        Calendar.DAY_OF_WEEK
                    ) - 1],false
                )
            )
            calendar.timeInMillis = currentTime + 86400000
            currentTime += 86400000
        }
        listOfDays[0].isChoose = true
        return listOfDays
    }

}