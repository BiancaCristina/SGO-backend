package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.TypeOfCostAggregation
import com.github.biancacristina.sgobackend.dto.TypeOfCostAggregationDTO
import com.github.biancacristina.sgobackend.repositories.TypeOfCostAggregationRepository
import com.github.biancacristina.sgobackend.services.exceptions.DataIntegrityException
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class TypeOfCostAggregationService {

    @Autowired
    private lateinit var typeOfCostAggregationRepository: TypeOfCostAggregationRepository

    fun findById(id: Long): TypeOfCostAggregation {
        var obj = typeOfCostAggregationRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Tipo de agregador de custo não encontrado!")
        }
    }

    fun insert(obj: TypeOfCostAggregation): TypeOfCostAggregation {
        obj.id = 0

        return typeOfCostAggregationRepository.save(obj)
    }

    fun updateName(
        objDTO: TypeOfCostAggregationDTO,
        id: Long) {
        var obj = this.findById(id)

        if (objDTO.name != "") obj.name = objDTO.name

        typeOfCostAggregationRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)

        try {
            typeOfCostAggregationRepository.deleteById(id)
        }

        catch(e: DataIntegrityViolationException) {
            throw DataIntegrityException("Não é possível excluir um tipo de agregador de custo que possui agregadores de custo associados.")
        }
    }

    fun fromDTO(objDTO: TypeOfCostAggregationDTO): TypeOfCostAggregation {
        var obj = TypeOfCostAggregation(objDTO.id, objDTO.name)

        return obj
    }
}