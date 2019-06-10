package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.State
import com.github.biancacristina.sgobackend.dto.StateDTO
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

    fun insert(obj: State): State {
        obj.id = 0

        return stateRepository.save(obj)
    }

    fun updateName(objDTO: StateDTO, id: Long) {
        var obj = this.findById(id)
        updateName(objDTO, obj)

        stateRepository.save(obj)
    }

    protected fun updateName(
            objDTO: StateDTO,
            obj: State) {

        if (objDTO.name != "") obj.name = objDTO.name

        // Add exception handler to deal with empty names
    }

    fun deleteById(id: Long) {
        this.findById(id)
        stateRepository.deleteById(id)

        // Add exception handler to deal with the case when the deletion is not possible
    }

    fun fromDTO(objDTO: StateDTO): State {
        // Convert a DTO
        var newObj = State(objDTO.id, objDTO.name)
        return newObj
    }
}