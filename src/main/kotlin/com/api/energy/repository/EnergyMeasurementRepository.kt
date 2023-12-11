package com.api.energy.repository

import com.api.energy.model.mongo.EnergyMeasurement
import java.time.LocalDateTime
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EnergyMeasurementRepository : MongoRepository<EnergyMeasurement, ObjectId> {
    fun findFirstByOrderByTimeStampDesc(): EnergyMeasurement

    fun findByTimeStampAfter(timeStamp: LocalDateTime): List<EnergyMeasurement>
}