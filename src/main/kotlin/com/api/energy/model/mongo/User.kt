package com.api.energy.model.mongo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
        @Id @Indexed
        val _id: ObjectId,
        val username: String,
        val password: String,
        val role: String,
        val name: String
) {
    fun toResponse(): UserResponse {
        return UserResponse(
                userId = this._id.toHexString(),
                username = this.username,
                role = this.role,
                name = this.name
        )
    }
}

data class UserResponse(
        val userId: String,
        val username: String,
        val role: String,
        val name: String
)
