package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository: JpaRepository<City, Long>