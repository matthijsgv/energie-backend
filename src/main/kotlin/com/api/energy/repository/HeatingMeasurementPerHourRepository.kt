package com.api.energy.repository

import com.api.energy.model.mongo.EnergyMeasurementPerHour
import com.api.energy.model.mongo.HeatingMeasurement
import com.api.energy.model.mongo.HeatingMeasurementPerHour
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HeatingMeasurementPerHourRepository : MongoRepository<HeatingMeasurementPerHour, ObjectId> {

    fun findByDateAndHour(date: String, hour: Int): HeatingMeasurementPerHour?

    fun findByDateOrderByHourAsc(date: String): List<HeatingMeasurementPerHour>

}