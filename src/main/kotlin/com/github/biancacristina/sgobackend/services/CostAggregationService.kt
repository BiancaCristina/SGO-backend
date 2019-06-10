package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.domain.CostAggregation
import com.github.biancacristina.sgobackend.dto.CostAggregationDTO
import com.github.biancacristina.sgobackend.repositories.CostAggregationRepository
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CostAggregationService {

    @Autowired
    private lateinit var costAggregationRepository: CostAggregationRepository

    @Autowired
    private lateinit var typeOfCostAggregationService: TypeOfCostAggregationService

    fun findById(id: Long): CostAggregation {
        var obj = costAggregationRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Agregador de custo não encontrado!")
        }
    }

    fun insert(obj: CostAggregation): CostAggregation {
        obj.id = 0

        return costAggregationRepository.save(obj)
    }

    fun updateName(
        objDTO: CostAggregationDTO,
        id: Long
    ) {
        var obj = this.findById(id)

        if (objDTO.name != "") obj.name = objDTO.name

        costAggregationRepository.save(obj)
    }

    fun updateTypeOfCostAggregation(
       idCostAggregation: Long,
       idTypeOfCostAggregation: Long
    ) {
        var obj = this.findById(idCostAggregation)
        var typeOfCostAggregation = typeOfCostAggregationService
                            .findById(idTypeOfCostAggregation)

        obj.typeOfCostAggregation = typeOfCostAggregation

        costAggregationRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)
        costAggregationRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: CostAggregationDTO): CostAggregation {
        var typeOfCostAggregation = typeOfCostAggregationService
                                    .findById(objDTO.id_typeOfCostAggregation!!)

        var obj = CostAggregation(
                0,
                objDTO.name,
                typeOfCostAggregation
        )

        return obj
    }
}