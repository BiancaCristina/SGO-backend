package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.TypeOfCostAggregation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeOfCostAggregationRepository: JpaRepository<TypeOfCostAggregation, Long>