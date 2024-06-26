package com.api.energy.service

import com.api.energy.common.DateUtilities
import com.api.energy.common.DateUtilities.getDateList
import com.api.energy.model.EnergyUsed
import com.api.energy.model.EnergyUsedHourly
import com.api.energy.model.EnergyUsedWeekly
import com.api.energy.model.HeatingUsedWeekly
import com.api.energy.model.dto.HeatingMeasurementDTO
import com.api.energy.model.dto.MeasurementDTO
import com.api.energy.model.mongo.*
import com.api.energy.repository.EnergyMeasurementPerHourRepository
import com.api.energy.repository.EnergyMeasurementRepository
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Logger
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EnergyService(
    private val energyMeasurementRepository: EnergyMeasurementRepository,
    private val energyMeasurementPerHourRepository: EnergyMeasurementPerHourRepository
) {

    private val log = LoggerFactory.getLogger(EnergyService::class.java)
    fun getAllMeasurements() = energyMeasurementRepository.findAll().map { it.toResponse() }

    fun postNewMeasurement(measurement: MeasurementDTO): EnergyMeasurementResponse {

        val response = energyMeasurementRepository.save(measurement.toDBModel())
        processHourlyMeasurement(response)
        return response.toResponse()
    }

    fun deleteAllMeasurements() = energyMeasurementRepository.deleteAll()

    fun processHourlyMeasurement(measurement: EnergyMeasurement) {
        val date = measurement.timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val hour = measurement.timeStamp.hour
        val existing = energyMeasurementPerHourRepository.findByDateAndHour(date, hour)

        if (existing == null) {
            energyMeasurementPerHourRepository.save(
                EnergyMeasurementPerHour(
                    _id = ObjectId(),
                    date = date,
                    hour = hour,
                    consumptieLaagTariefStartValue = measurement.consumptieLaagTarief,
                    consumptieLaagTariefLastValue = measurement.consumptieLaagTarief,
                    consumptieLaagTariefDifference = 0,
                    consumptieHoogTariefStartValue = measurement.consumptieHoogTarief,
                    consumptieHoogTariefLastValue = measurement.consumptieHoogTarief,
                    consumptieHoogTariefDifference = 0,
                    retourLeveringLaagTariefStartValue = measurement.retourLeveringLaagTarief,
                    retourLeveringLaagTariefLastValue = measurement.retourLeveringLaagTarief,
                    retourLeveringLaagTariefDifference = 0,
                    retourLeveringHoogTariefStartValue = measurement.retourLeveringHoogTarief,
                    retourLeveringHoogTariefLastValue = measurement.retourLeveringHoogTarief,
                    retourLeveringHoogTariefDifference = 0
                )
            )
        } else {
            energyMeasurementPerHourRepository.save(
                existing.copy(
                    consumptieLaagTariefLastValue = measurement.consumptieLaagTarief,
                    consumptieLaagTariefDifference = measurement.consumptieLaagTarief - existing.consumptieLaagTariefStartValue,
                    consumptieHoogTariefLastValue = measurement.consumptieHoogTarief,
                    consumptieHoogTariefDifference = measurement.consumptieHoogTarief - existing.consumptieHoogTariefStartValue,
                    retourLeveringLaagTariefLastValue = measurement.retourLeveringLaagTarief,
                    retourLeveringLaagTariefDifference = measurement.retourLeveringLaagTarief - existing.retourLeveringLaagTariefStartValue,
                    retourLeveringHoogTariefLastValue = measurement.retourLeveringHoogTarief,
                    retourLeveringHoogTariefDifference = measurement.retourLeveringHoogTarief - existing.retourLeveringHoogTariefStartValue
                )
            )
        }
    }

    fun getDataForRange(startDate: String, endDate: String): Map<String, EnergyUsed> {
        val datesList = getDateList(startDate, endDate)

        val dateEnergyMap = mutableMapOf<String, EnergyUsed>()


        datesList.forEach{
            val energyUsed = getDaySummary(it)
            dateEnergyMap[it] = energyUsed
        }

        return dateEnergyMap
    }
    fun getDaySummary(date: String): EnergyUsed {
        val oneDay = energyMeasurementPerHourRepository.findByDate(date)
        return EnergyUsed(oneDay.sumOf { it.consumptieLaagTariefDifference },
            oneDay.sumOf { it.consumptieHoogTariefDifference },
            oneDay.sumOf { it.retourLeveringLaagTariefDifference },
            oneDay.sumOf { it.retourLeveringHoogTariefDifference })
    }

    fun getLastMeasurement(): EnergyMeasurement {
        return energyMeasurementRepository.findFirstByOrderByTimeStampDesc()
    }

    fun getHourlyDataForDateRange(startDate: String, endDate: String): List<EnergyUsedHourly> {
        val datesList = getDateList(startDate, endDate)

        val result = mutableListOf<EnergyUsedHourly>()
        datesList.forEach{
            day ->
                val dayList = energyMeasurementPerHourRepository.findByDateOrderByHourAsc(day)
                if (dayList.isEmpty()) {
                    for (i in 0..23){
                        result.add(EnergyUsedHourly(day, i, EnergyUsed()))
                    }
                } else {
                    dayList.forEach {
                        meas ->
                        result.add(EnergyUsedHourly(meas.date,meas.hour,EnergyUsed(
                            meas.consumptieLaagTariefDifference,
                            meas.consumptieHoogTariefDifference,
                            meas.retourLeveringLaagTariefDifference,
                            meas.retourLeveringHoogTariefDifference
                        )))
                    }
                }

        }
        return result
    }

    fun getLastHour(): List<MeasurementResponse> {
        val now = LocalDateTime.now()
        val oneHourAgo = now.minusHours(1)
        return energyMeasurementRepository.findByTimeStampAfter(oneHourAgo).map { MeasurementResponse(timeStamp =  it.timeStamp.toString(), werkelijkVerbruik = it.werkelijkVerbruik, werkelijkeRetourLevering = it.werkelijkeRetourLevering ) }
    }

    fun getWeekData(startDate: Calendar, endDate: Calendar): List<EnergyMeasurementPerHour> {
        val format = SimpleDateFormat("yyyy-MM-dd")

        return energyMeasurementPerHourRepository.findByDateBetween(format.format(startDate.time), format.format(endDate.time
        ))
    }

    fun getDataPerWeek(endDate: String, weeks: Int): List<EnergyUsedWeekly> {
        val result = mutableListOf<EnergyUsedWeekly>()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(endDate)
        val cal = Calendar.getInstance(Locale.getDefault())
        cal.time = date
        for (i in 1..weeks) {
            val weekNumber = DateUtilities.getWeekNumber(cal)
            val year = cal.get(Calendar.YEAR)
            val dates = DateUtilities.getFirstDayOfWeekByWeekNumber(year, weekNumber)
            val weekData = getWeekData(dates.first, dates.second)
            val energyUsedWeekly = EnergyUsedWeekly(
                    week = weekNumber,
                    year = year,
                    startDate = format.format(dates.first.time),
                    endDate = format.format(dates.second.time),
                    verbruikLaagTarief = weekData.sumOf {
                                                        it.consumptieLaagTariefDifference
                    },
                    verbruikHoogTarief = weekData.sumOf {
                        it.consumptieHoogTariefDifference
                    },
                    terugLeveringLaagTarief = weekData.sumOf {
                        it.retourLeveringLaagTariefDifference
                    },
                    terugLeveringHoogTarief = weekData.sumOf {
                        it.retourLeveringHoogTariefDifference
                    }
            )
            result.add(energyUsedWeekly)
            cal.add(Calendar.WEEK_OF_YEAR, -1)
        }
        return result.reversed()
    }

}