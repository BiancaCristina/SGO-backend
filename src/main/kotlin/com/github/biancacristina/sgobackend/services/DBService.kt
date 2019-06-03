package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.domain.State
import com.github.biancacristina.sgobackend.repositories.CityRepository
import com.github.biancacristina.sgobackend.repositories.StateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DBService {

    @Autowired
    private lateinit var cityRepository: CityRepository

    @Autowired
    private lateinit var stateRepository: StateRepository

    fun instantiateTestDataBase(): Unit {
        var e1 = State(0, "Minas Gerais")
        var e2 = State(0, "Rio de Janeiro")
        var e3 = State(0, "São Paulo")

        stateRepository.saveAll(Arrays.asList(e1,e2,e3))

        var c1 = City(0, "Uberlândia", e1)
        var c2 = City(0, "Belo Horizonte", e1)
        var c3 = City(0, "São Paulo", e3)

        cityRepository.saveAll(Arrays.asList(c1,c2,c3))
        stateRepository.saveAll(Arrays.asList(e1,e2,e3))
    }
}