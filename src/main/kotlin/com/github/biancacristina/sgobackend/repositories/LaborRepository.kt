package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.Labor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LaborRepository: JpaRepository<Labor, Long>