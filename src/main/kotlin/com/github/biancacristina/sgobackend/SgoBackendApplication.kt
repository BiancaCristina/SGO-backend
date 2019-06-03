package com.github.biancacristina.sgobackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SgoBackendApplication

fun main(args: Array<String>) {
	runApplication<SgoBackendApplication>(*args)
}
