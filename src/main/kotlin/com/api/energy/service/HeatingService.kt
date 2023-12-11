package com.api.energy.service

import com.api.energy.common.DateUtilities
import com.api.energy.common.DateUtilities.getDateList
import com.api.energy.common.DateUtilities.getWeekNumber
import com.api.energy.model.HeatingUsedWeekly
import com.api.energy.model.dto.HeatingMeasurementDTO
import com.api.energy.model.mongo.HeatingMeasurement
import com.api.energy.model.mongo.HeatingMeasurementPerHour
import com.api.energy.model.mongo.HeatingResponse
import com.api.energy.repository.HeatingMeasurementPerHourRepository
import com.api.energy.repository.HeatingMeasurementRepository
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class HeatingService(
    private val heatingMeasurementRepository: HeatingMeasurementRepository,
    private val heatingMeasurementPerHourRepository: HeatingMeasurementPerHourRepository
) {

    fun postHeatingMeasurement(heatingMeasurementDTO: HeatingMeasurementDTO) {

        val measurement = heatingMeasurementRepository.save(
            HeatingMeasurement(
                ObjectId(),
                LocalDateTime.now(),
                heatingMeasurementDTO.currentHeatingValue
            )
        )

        val date = measurement.timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val hour = measurement.timeStamp.hour

        val existing = heatingMeasurementPerHourRepository.findByDateAndHour(date, hour)

        if (existing == null) {
            heatingMeasurementPerHourRepository.save(
                HeatingMeasurementPerHour(
                    ObjectId(),
                    date,
                    hour,
                    heatingMeasurementDTO.currentHeatingValue,
                    heatingMeasurementDTO.currentHeatingValue,
                    0
                )
            )
        } else {
            heatingMeasurementPerHourRepository.save(
                existing.copy(heatingLastValue = heatingMeasurementDTO.currentHeatingValue, heatingDifference = heatingMeasurementDTO.currentHeatingValue - existing.heatingStartValue)
            )
        }
    }

    fun getDataPerHourForRange(startDate: String, endDate: String):List<HeatingResponse> {
        val dateList = getDateList(startDate, endDate)

        val result = mutableListOf<HeatingResponse>()

        dateList.forEach {
            val dailyData = heatingMeasurementPerHourRepository.findByDateOrderByHourAsc(it).map{it.toResponse()}
            result.addAll(dailyData)
        }
        return result
    }

    fun getWeekData(startDate: Calendar, endDate: Calendar): List<HeatingMeasurementPerHour> {
        val format = SimpleDateFormat("yyyy-MM-dd")

        return heatingMeasurementPerHourRepository.findByDateBetween(format.format(startDate.time), format.format(endDate.time
        ))
    }



    fun getDataPerWeek(endDate: String, weeks: Int): List<HeatingUsedWeekly> {
        val result = mutableListOf<HeatingUsedWeekly>()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(endDate)
        val cal = Calendar.getInstance(Locale.getDefault())
        cal.time = date
        for (i in 1..weeks) {
            val weekNumber = getWeekNumber(cal)
            val year = cal.get(Calendar.YEAR)
            val dates = DateUtilities.getFirstDayOfWeekByWeekNumber(year, weekNumber)
            val weekData = getWeekData(dates.first, dates.second)
            val heatingUsedWeekly = HeatingUsedWeekly(
                week = weekNumber,
                year = year,
                startDate = format.format(dates.first.time),
                endDate = format.format(dates.second.time),
                heatingUsed = weekData.sumOf {
                    it.heatingDifference
                }
            )
            result.add(heatingUsedWeekly)
            cal.add(Calendar.WEEK_OF_YEAR, -1)
        }
        return result.reversed()
    }
}
