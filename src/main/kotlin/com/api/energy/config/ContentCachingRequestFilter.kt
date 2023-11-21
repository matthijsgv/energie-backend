package com.api.energy.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper

class ContentCachingRequestWrapperFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Wrap the request with ContentCachingRequestWrapper
        val wrappedRequest = ContentCachingRequestWrapper(request)

        // Continue with the filter chain
        filterChain.doFilter(wrappedRequest, response)
    }
}