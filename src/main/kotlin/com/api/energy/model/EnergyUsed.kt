package com.api.energy.model

data class EnergyUsed(
    val consumptieLaagTariefDifference: Long = 0,
    val consumptieHoogTariefDifference: Long = 0,
    val retourLeveringLaagTariefDifference: Long = 0,
    val retourLeveringHoogTariefDifference: Long = 0,
)