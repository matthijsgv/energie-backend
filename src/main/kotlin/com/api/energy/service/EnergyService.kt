package com.api.energy.service

import com.api.energy.model.dto.MeasurementDTO
import com.api.energy.model.mongo.EnergyMeasurement
import com.api.energy.model.mongo.EnergyMeasurementPerHour
import com.api.energy.model.mongo.EnergyMeasurementResponse
import com.api.energy.repository.EnergyMeasurementPerHourRepository
import com.api.energy.repository.EnergyMeasurementRepository
import java.time.format.DateTimeFormatter
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class EnergyService(
    private val energyMeasurementRepository: EnergyMeasurementRepository,
    private val energyMeasurementPerHourRepository: EnergyMeasurementPerHourRepository
) {

    fun getAllMeasurements() = energyMeasurementRepository.findAll().map { it.toResponse() }

    fun postNewMeasurement(measurement: MeasurementDTO): EnergyMeasurementResponse {

        val response = energyMeasurementRepository.save(measurement.toDBModel())
        processHourlyMeasurement(response)
        return response.toResponse()
    }

    fun deleteAllMeasurements() = energyMeasurementRepository.deleteAll()

    fun processHourlyMeasurement(measurement: EnergyMeasurement) {
        val date = measurement.timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val hour = measurement.timeStamp.hour

        val existing = energyMeasurementPerHourRepository.findByDateAndHour(date, hour)

        if (existing == null) {
            energyMeasurementPerHourRepository.save(
                EnergyMeasurementPerHour(
                    _id = ObjectId(),
                    date = date,
                    hour = hour,
                    consumptieLaagTariefStartValue = measurement.consumptieLaagTarief,
                    consumptieLaagTariefLastValue = measurement.consumptieLaagTarief,
                    consumptieLaagTariefDifference = 0,
                    consumptieHoogTariefStartValue = measurement.consumptieHoogTarief,
                    consumptieHoogTariefLastValue = measurement.consumptieHoogTarief,
                    consumptieHoogTariefDifference = 0,
                    retourLeveringLaagTariefStartValue = measurement.retourLeveringLaagTarief,
                    retourLeveringLaagTariefLastValue = measurement.retourLeveringLaagTarief,
                    retourLeveringLaagTariefDifference = 0,
                    retourLeveringHoogTariefStartValue = measurement.retourLeveringHoogTarief,
                    retourLeveringHoogTariefLastValue = measurement.retourLeveringHoogTarief,
                    retourLeveringHoogTariefDifference = 0
                )
            )
        } else {
            energyMeasurementPerHourRepository.save(
                existing.copy(
                    consumptieLaagTariefLastValue = measurement.consumptieLaagTarief,
                    consumptieLaagTariefDifference = measurement.consumptieLaagTarief - existing.consumptieLaagTariefStartValue,
                    consumptieHoogTariefLastValue = measurement.consumptieHoogTarief,
                    consumptieHoogTariefDifference = measurement.consumptieHoogTarief - existing.consumptieHoogTariefStartValue,
                    retourLeveringLaagTariefLastValue = measurement.retourLeveringLaagTarief,
                    retourLeveringLaagTariefDifference = measurement.retourLeveringLaagTarief - existing.retourLeveringLaagTariefStartValue,
                    retourLeveringHoogTariefLastValue = measurement.retourLeveringHoogTarief,
                    retourLeveringHoogTariefDifference = measurement.retourLeveringHoogTarief - existing.retourLeveringHoogTariefStartValue
                )
            )
        }


    }
}