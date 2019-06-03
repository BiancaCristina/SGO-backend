package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.repositories.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService {

    @Autowired
    private lateinit var cityRepository: CityRepository

    fun findById(id: Long): City {
        return cityRepository.findById(id).orElse(null)
    }
}