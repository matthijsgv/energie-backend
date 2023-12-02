package com.api.energy.model

data class EnergyUsedHourly (
    val date: String,
    val hour: Int,
    val energyUsed: EnergyUsed
        )