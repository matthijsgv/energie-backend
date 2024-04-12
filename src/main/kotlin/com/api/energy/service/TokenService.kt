package com.api.energy.service

import com.api.energy.error.TokenParsingException
import com.api.energy.model.mongo.UserResponse
import com.google.gson.Gson
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenService (
        private val jwtDecoder: JwtDecoder,
        private val jwtEncoder: JwtEncoder,
        private val gson: Gson
) {

    fun createToken(user: UserResponse): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
                .issuedAt(Instant.now()).expiresAt(Instant.now().plus(999L, ChronoUnit.DAYS))
                .claim("userData", gson.toJson(user))
                .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String): UserResponse {
        try {
            val jwt = jwtDecoder.decode(token)
            val userData = jwt.claims["userData"] as String
            println(userData)
            return gson.fromJson(userData, UserResponse::class.java)
        } catch (e: Exception) {
            throw TokenParsingException("Token could not be parsed")
        }
    }

}