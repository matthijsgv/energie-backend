package com.api.energy.controller

import com.api.energy.model.EnergyUsed
import com.api.energy.model.mongo.EnergyMeasurementResponse
import com.api.energy.model.dto.MeasurementDTO
import com.api.energy.service.EnergyService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/measurement")
class EnergyController(private val energyService: EnergyService) {

    private val log = LoggerFactory.getLogger(EnergyController::class.java)

    @GetMapping("/all")
    fun getAllMeasurements() = energyService.getAllMeasurements()

    @GetMapping("/daily/{date}")
    fun getDailySummary(@PathVariable date: String) = energyService.getDaySummary(date)

    @PostMapping
    fun addNewMeasurement(@RequestBody measurement: MeasurementDTO): EnergyMeasurementResponse {
        log.info("New measurement: $measurement")
        return energyService.postNewMeasurement(measurement)
    }

    @GetMapping("/range")
    fun getDataForRange(
        @RequestParam(required = true) startDate: String,
        @RequestParam(required = true) endDate: String
    ): Map<String, EnergyUsed> {
        log.info("Getting range from $startDate to $endDate")
        return energyService.getDataForRange(startDate, endDate)
    }


    @GetMapping("/last")
    fun getLastMeasurement() = energyService.getLastMeasurement().toResponse()

    @GetMapping("/hourly/range")
    fun getHourlyRange(@RequestParam(required = true) startDate: String,@RequestParam(required = true) endDate: String ) =
        energyService.getHourlyDataForDateRange(startDate, endDate)

    @GetMapping("/last-hour")
    fun getLastHour() = energyService.getLastHour()

    @GetMapping("/weekly")
    fun getWeekly(@RequestParam endDate: String,
                  @RequestParam size: Int) = energyService.getDataPerWeek(endDate, size )
}