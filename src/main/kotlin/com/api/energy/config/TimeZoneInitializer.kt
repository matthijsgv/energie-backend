package com.api.energy.config

import jakarta.annotation.PostConstruct
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class TimeZoneInitializer {
    private val log = LoggerFactory.getLogger(TimeZoneInitializer::class.java)
    @PostConstruct
    fun init() {
        // Set the default timezone for the entire application
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Amsterdam"))
        log.info("Using time zone '{}'", TimeZone.getDefault().id)
    }
}