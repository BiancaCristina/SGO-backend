package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.TypeOfCostAggregation
import com.github.biancacristina.sgobackend.dto.TypeOfCostAggregationDTO
import com.github.biancacristina.sgobackend.repositories.TypeOfCostAggregationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TypeOfCostAggregationService {

    @Autowired
    private lateinit var typeOfCostAggregationRepository: TypeOfCostAggregationRepository

    fun findById(id: Long): TypeOfCostAggregation {
        return typeOfCostAggregationRepository.findById(id).orElse(null)
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
        typeOfCostAggregationRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: TypeOfCostAggregationDTO): TypeOfCostAggregation {
        var obj = TypeOfCostAggregation(objDTO.id, objDTO.name)

        return obj
    }
}