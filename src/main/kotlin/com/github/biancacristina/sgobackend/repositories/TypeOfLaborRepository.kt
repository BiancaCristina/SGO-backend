package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.TypeOfLabor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeOfLaborRepository: JpaRepository<TypeOfLabor, Long>