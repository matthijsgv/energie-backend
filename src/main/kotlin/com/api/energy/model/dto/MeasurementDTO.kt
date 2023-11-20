package com.api.energy.model.dto

import com.api.energy.model.EnergyMeasurement
import java.time.LocalDateTime
import org.bson.types.ObjectId

data class MeasurementDTO(
    val consumptieLaagTarief: Long,
    val consumptieHoogTarief: Long,
    val retourLeveringLaagTarief: Long,
    val retourLeveringHoogTarief: Long,
    val werkelijkVerbruik: Long,
    val werkelijkeRetourLevering: Long
) {
    fun toDBModel() = EnergyMeasurement(
        _id = ObjectId(),
        timeStamp = LocalDateTime.now(),
        consumptieLaagTarief,
        consumptieHoogTarief,
        retourLeveringLaagTarief,
        retourLeveringHoogTarief,
        werkelijkVerbruik,
        werkelijkeRetourLevering
    )
}