package com.api.energy.repository

import com.api.energy.model.EnergyMeasurement
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EnergyMeasurementRepository : MongoRepository<EnergyMeasurement, ObjectId>