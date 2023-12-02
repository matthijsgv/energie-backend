package com.api.energy.model.dto

data class HeatingMeasurementDTO(
    val currentHeatingValue: Long
) {
    // Default (no-argument) constructor
    constructor() : this(0) // You can initialize the property with a default value
}