package com.api.energy.repository

import com.api.energy.model.mongo.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, ObjectId> {
    fun findByUsername(username: String): User?

}