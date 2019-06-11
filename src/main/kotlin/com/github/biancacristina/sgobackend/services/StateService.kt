package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.State
import com.github.biancacristina.sgobackend.dto.StateDTO
import com.github.biancacristina.sgobackend.repositories.StateRepository
import com.github.biancacristina.sgobackend.services.exceptions.DataIntegrityException
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class StateService {

    @Autowired
    private lateinit var stateRepository: StateRepository

    fun findById(id: Long): State {
        var obj = stateRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Estado não encontrado.")
        }
    }

    fun findAllPaged(
        page: Int,
        linesPerPage: Int,
        direction: String,
        orderBy: String
    ): Page<State> {
        var pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        )

        return stateRepository.findAll(pageRequest)
    }

    fun insert(obj: State): State {
        obj.id = 0

        return stateRepository.save(obj)
    }

    fun updateName(objDTO: StateDTO, id: Long) {
        var obj = this.findById(id)

        obj.name = objDTO.name

        stateRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)

        try {
            stateRepository.deleteById(id)
        }

        catch(e: DataIntegrityViolationException) {
            throw DataIntegrityException("Não é possível excluir um estado que possui cidades associadas.")
        }
    }

    fun fromDTO(objDTO: StateDTO): State {
        // Convert a DTO
        var newObj = State(objDTO.id, objDTO.name)
        return newObj
    }
}