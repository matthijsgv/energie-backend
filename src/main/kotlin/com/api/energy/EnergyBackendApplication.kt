package com.api.energy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EnergyBackendApplication

fun main(args: Array<String>) {
	runApplication<EnergyBackendApplication>(*args)
}
