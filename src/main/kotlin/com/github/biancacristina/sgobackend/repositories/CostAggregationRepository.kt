package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.CostAggregation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CostAggregationRepository: JpaRepository<CostAggregation, Long>