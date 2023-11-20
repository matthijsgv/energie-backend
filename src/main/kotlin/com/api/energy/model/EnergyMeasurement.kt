package com.api.energy.model

import java.time.LocalDateTime
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "energy-measurements-per-minute")
data class EnergyMeasurement(
    @Id @Indexed val _id: ObjectId,
    val timeStamp: LocalDateTime,
    val consumptieLaagTarief: Long,
    val consumptieHoogTarief: Long,
    val retourLeveringLaagTarief: Long,
    val retourLeveringHoogTarief: Long,
    val werkelijkVerbruik: Long,
    val werkelijkeRetourLevering: Long
) {


    fun toResponse() = EnergyMeasurementResponse(
        this._id.toHexString(),
        this.timeStamp,
        this.consumptieLaagTarief,
        this.consumptieHoogTarief,
        this.retourLeveringLaagTarief,
        this.retourLeveringHoogTarief,
        this.werkelijkVerbruik,
        this.werkelijkeRetourLevering
    )
}

data class EnergyMeasurementResponse(
    val id: String,
    val timeStamp: LocalDateTime,
    val consumptieLaagTarief: Long,
    val consumptieHoogTarief: Long,
    val retourLeveringLaagTarief: Long,
    val retourLeveringHoogTarief: Long,
    val werkelijkVerbruik: Long,
    val werkelijkeRetourLevering: Long
)