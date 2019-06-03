package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.State
import com.github.biancacristina.sgobackend.repositories.StateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StateService {

    @Autowired
    private lateinit var stateRepository: StateRepository

    fun findById(id: Long): State {
        return stateRepository.findById(id).orElse(null)
    }
}