package com.api.energy.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.nio.charset.StandardCharsets
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.ContentCachingRequestWrapper

class LoggingInterceptor : HandlerInterceptor {

    val log = LoggerFactory.getLogger("requestLogger")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // Log the incoming request details
        val requestInfo = """
            Request URL: ${request.requestURL}
            Method: ${request.method}
            Headers: ${request.headerNames.toList().associateWith { request.getHeader(it) }}
        """.trimIndent()

        println(requestInfo)

        // Log the request body for POST requests
        if (request.method.equals("POST", ignoreCase = true)) {
            val contentCachingRequestWrapper = request as ContentCachingRequestWrapper
            val requestBody = contentCachingRequestWrapper.contentAsByteArray.toString(StandardCharsets.UTF_8)
            println("Request Body: $requestBody")
        }

        return true
    }
}