package com.api.energy.repository

import com.api.energy.model.mongo.EnergyMeasurementPerHour
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EnergyMeasurementPerHourRepository: MongoRepository<EnergyMeasurementPerHour, ObjectId> {
    fun findByDateAndHour(date: String, hour: Int): EnergyMeasurementPerHour?

    fun findByDate(date: String): List<EnergyMeasurementPerHour>

    fun findByDateOrderByHourAsc(date: String): List<EnergyMeasurementPerHour>
}