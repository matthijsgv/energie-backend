package com.api.energy.service

import com.api.energy.error.DuplicateUserException
import com.api.energy.error.IncorrectPasswordException
import com.api.energy.error.UserNotFoundException
import com.api.energy.model.dto.LoginDTO
import com.api.energy.model.dto.RegisterDTO
import com.api.energy.model.mongo.User
import com.api.energy.model.mongo.UserResponse
import com.api.energy.model.response.LoginResponse
import com.api.energy.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
       private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val tokenService: TokenService
) {


    fun registerUser(userInfo: RegisterDTO) {
        if (userRepository.findByUsername(userInfo.username) != null) {
            throw DuplicateUserException("User already exists with this username")
        }

        userRepository.save(User(
                _id = ObjectId(),
                username = userInfo.username,
                password = passwordEncoder.encode(userInfo.password),
                role = userInfo.role,
                name = userInfo.name
        ))
    }

    fun login(loginDTO: LoginDTO): LoginResponse {

        val userData = userRepository.findByUsername(loginDTO.username)
        require( userData != null) {
            throw UserNotFoundException("No user found with this username")
        }
        require(passwordEncoder.matches(loginDTO.password, userData.password)) {
            throw IncorrectPasswordException("Incorrect password provided")
        }
        val response = userData.toResponse()
        return LoginResponse(response.userId,response.username,response.name,response.role,tokenService.createToken(response))
    }

    fun authorize(token: String) = tokenService.parseToken(token)
}
