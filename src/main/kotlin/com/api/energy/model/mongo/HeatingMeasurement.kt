package com.api.energy.model.mongo

import java.time.LocalDateTime
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "heating-measurements")
data class HeatingMeasurement(
    @Id @Indexed val _id: ObjectId,
    val timeStamp: LocalDateTime,
    val heatingUsageTotal: Long,
    )
