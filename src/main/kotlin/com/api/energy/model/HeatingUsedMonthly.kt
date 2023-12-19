package com.api.energy.model

data class HeatingUsedMonthly(
    val year: Int,
    val month: Int,
    val heatingUsed: Long = 0
)
