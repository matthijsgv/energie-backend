package com.api.energy.service

import com.api.energy.model.dto.HeatingMeasurementDTO
import com.api.energy.model.mongo.HeatingMeasurement
import com.api.energy.model.mongo.HeatingMeasurementPerHour
import com.api.energy.repository.HeatingMeasurementPerHourRepository
import com.api.energy.repository.HeatingMeasurementRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class HeatingService(
    private val heatingMeasurementRepository: HeatingMeasurementRepository,
    private val heatingMeasurementPerHourRepository: HeatingMeasurementPerHourRepository
) {

    fun postHeatingMeasurement(heatingMeasurementDTO: HeatingMeasurementDTO) {

        val measurement = heatingMeasurementRepository.save(
            HeatingMeasurement(
                ObjectId(),
                LocalDateTime.now(),
                heatingMeasurementDTO.currentHeatingValue
            )
        )

        val date = measurement.timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val hour = measurement.timeStamp.hour

        val existing = heatingMeasurementPerHourRepository.findByDateAndHour(date, hour)

        if (existing == null) {
            heatingMeasurementPerHourRepository.save(
                HeatingMeasurementPerHour(
                    ObjectId(),
                    date,
                    hour,
                    heatingMeasurementDTO.currentHeatingValue,
                    heatingMeasurementDTO.currentHeatingValue,
                    0
                )
            )
        } else {
            heatingMeasurementPerHourRepository.save(
                existing.copy(heatingLastValue = heatingMeasurementDTO.currentHeatingValue, heatingDifference = heatingMeasurementDTO.currentHeatingValue - existing.heatingStartValue)
            )
        }
    }
}