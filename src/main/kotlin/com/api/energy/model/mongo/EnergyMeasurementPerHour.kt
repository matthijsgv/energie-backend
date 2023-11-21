package com.api.energy.model.mongo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "energy-measurement-per-hour")
data class EnergyMeasurementPerHour(
    @Id @Indexed val _id: ObjectId,
    val date: String,
    val hour: Int,
    val consumptieLaagTariefStartValue: Long,
    val consumptieLaagTariefLastValue: Long,
    val consumptieLaagTariefDifference: Long,
    val consumptieHoogTariefStartValue: Long,
    val consumptieHoogTariefLastValue: Long,
    val consumptieHoogTariefDifference: Long,
    val retourLeveringLaagTariefStartValue: Long,
    val retourLeveringLaagTariefLastValue: Long,
    val retourLeveringLaagTariefDifference: Long,
    val retourLeveringHoogTariefStartValue: Long,
    val retourLeveringHoogTariefLastValue: Long,
    val retourLeveringHoogTariefDifference: Long,

    )
