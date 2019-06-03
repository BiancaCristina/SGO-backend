package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StateRepository: JpaRepository<State, Long>