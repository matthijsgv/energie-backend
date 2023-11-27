package com.api.energy.model

data class EnergyUsed(
    val consumptieLaagTariefDifference: Long,
    val consumptieHoogTariefDifference: Long,
    val retourLeveringLaagTariefDifference: Long,
    val retourLeveringHoogTariefDifference: Long,
)