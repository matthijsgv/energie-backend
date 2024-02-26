package com.api.energy.repository

import com.api.energy.model.mongo.EnergyMeasurementPerHour
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EnergyMeasurementPerHourRepository: MongoRepository<EnergyMeasurementPerHour, ObjectId> {
    fun findByDateAndHour(date: String, hour: Int): EnergyMeasurementPerHour?

    fun findByDate(date: String): List<EnergyMeasurementPerHour>

    @Query("{ 'date': { '\$gte': ?0, '\$lte': ?1 } }")
    fun findByDateBetween(startDate: String, endDate: String): List<EnergyMeasurementPerHour>

    fun findByDateOrderByHourAsc(date: String): List<EnergyMeasurementPerHour>
}