package com.api.energy.common

import java.text.SimpleDateFormat
import java.util.*

object DateUtilities {

    fun getFirstDayOfWeekByWeekNumber(year: Int, weekNumber: Int): Pair<Calendar, Calendar> {

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val startDate = calendar.clone() as Calendar

        calendar.add(Calendar.DAY_OF_WEEK, 6)

        val endDate = calendar.clone() as Calendar

        return Pair(startDate, endDate)
    }

    fun getWeekNumber(date: Calendar): Int {
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date.add(Calendar.DAY_OF_WEEK, -1)
        }
        return date.get(Calendar.WEEK_OF_YEAR)
    }

    fun getDateList(startDate: String, endDate: String): List<String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startCalendar = Calendar.getInstance()
        val endCalendar = Calendar.getInstance()

        startCalendar.time = dateFormat.parse(startDate)!!
        endCalendar.time = dateFormat.parse(endDate)!!

        val datesList = mutableListOf<String>()

        while (!startCalendar.after(endCalendar)) {
            val currentDate = dateFormat.format(startCalendar.time)
            datesList.add(currentDate)
            startCalendar.add(Calendar.DATE, 1)
        }
        return datesList
    }

}