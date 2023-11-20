package com.api.energy.service

import com.api.energy.model.EnergyMeasurement
import com.api.energy.model.dto.MeasurementDTO
import com.api.energy.repository.EnergyMeasurementRepository
import org.springframework.stereotype.Service

@Service
class EnergyService(private val energyMeasurementRepository: EnergyMeasurementRepository) {

    fun getAllMeasurements() = energyMeasurementRepository.findAll().map { it.toResponse() }

    fun postNewMeasurement(measurement: MeasurementDTO) =
        energyMeasurementRepository.save(measurement.toDBModel()).toResponse()

}