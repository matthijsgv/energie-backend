package com.api.energy.model

data class HeatingUsedWeekly(
    val week: Int,
    val year: Int,
    val startDate: String,
    val endDate: String,
    val heatingUsed: Long
)
