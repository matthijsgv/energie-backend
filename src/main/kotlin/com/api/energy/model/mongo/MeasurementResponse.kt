package com.api.energy.model.mongo

data class MeasurementResponse(
    val timeStamp: String, val werkelijkVerbruik: Long, val werkelijkeRetourLevering: Long
)