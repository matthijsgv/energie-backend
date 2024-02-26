package com.api.energy.model

data class EnergyUsedWeekly(
        val week: Int,
        val year: Int,
        val startDate: String,
        val endDate: String,
        val verbruikLaagTarief: Long,
        val terugLeveringLaagTarief: Long,
        val verbruikHoogTarief: Long,
        val terugLeveringHoogTarief: Long,
)
