package com.api.energy.controller

import com.api.energy.model.EnergyMeasurement
import com.api.energy.model.EnergyMeasurementResponse
import com.api.energy.model.dto.MeasurementDTO
import com.api.energy.service.EnergyService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EnergyController(private val energyService: EnergyService) {

    private val log = LoggerFactory.getLogger(EnergyController::class.java)

    @GetMapping("/api/measurement")
    fun getAllMeasurements() = energyService.getAllMeasurements()

    @PostMapping("/api/measurement")
    fun addNewMeasurement(@RequestBody measurement: MeasurementDTO): EnergyMeasurementResponse {
        log.info(measurement.toString())
        return energyService.postNewMeasurement(measurement)
    }
}