package com.api.energy.repository

import com.api.energy.model.mongo.EnergyMeasurementPerHour
import com.api.energy.model.mongo.HeatingMeasurement
import com.api.energy.model.mongo.HeatingMeasurementPerHour
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface HeatingMeasurementPerHourRepository : MongoRepository<HeatingMeasurementPerHour, ObjectId> {

    fun findByDateAndHour(date: String, hour: Int): HeatingMeasurementPerHour?

    fun findByDateOrderByHourAsc(date: String): List<HeatingMeasurementPerHour>

    @Query("{ 'date': { '\$gte': ?0, '\$lte': ?1 } }")
    fun findByDateBetween(startDate: String, endDate: String): List<HeatingMeasurementPerHour>

    fun findByDate(date: String): List<HeatingMeasurementPerHour>
}