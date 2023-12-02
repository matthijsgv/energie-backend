package com.api.energy.repository

import com.api.energy.model.mongo.HeatingMeasurement
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HeatingMeasurementRepository : MongoRepository<HeatingMeasurement, ObjectId> {
}