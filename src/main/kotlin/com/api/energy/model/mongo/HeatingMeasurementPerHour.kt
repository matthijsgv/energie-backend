package com.api.energy.model.mongo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "heating-measurement-per-hour")
data class HeatingMeasurementPerHour(
    @Id @Indexed val _id: ObjectId,
    val date: String,
    val hour: Int,
    val heatingStartValue: Long,
    val heatingLastValue: Long,
    val heatingDifference: Long
    ) {
    fun toResponse() = HeatingResponse(this._id.toHexString(), this.date, this.hour, this.heatingDifference)
}
data class HeatingResponse(
    val id: String,
    val date: String,
    val hour: Int,
    val heatingDiffence: Long

)
