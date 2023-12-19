package com.api.energy.controller

import com.api.energy.model.dto.HeatingMeasurementDTO
import com.api.energy.service.HeatingService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/heating")
class HeatingController(private val heatingService: HeatingService) {

    val log = LoggerFactory.getLogger("test")

    @PostMapping("/measurement")
    fun postEnergyMeasurement(@RequestBody heatingMeasurement: HeatingMeasurementDTO) {
        log.info(heatingMeasurement.toString())
        heatingService.postHeatingMeasurement(heatingMeasurement)
    }

    @GetMapping("/hourly")
    fun getDataForRange(@RequestParam startDate: String, @RequestParam endDate: String) =
        heatingService.getDataPerHourForRange(startDate, endDate)

    @GetMapping("/daily")
    fun getDailyDataForRange(@RequestParam startDate: String, @RequestParam endDate: String) =
        heatingService.getDataPerDayForRange(startDate, endDate)
    @GetMapping("/weekly")
    fun getWeeklyData(
        @RequestParam endDate: String,
        @RequestParam size: Int,
    ) = heatingService.getDataPerWeek(endDate, size)

    @GetMapping("/monthly")
    fun getMonthlyData(
        @RequestParam months: Int
    ) = heatingService.getMonthData(months)


}