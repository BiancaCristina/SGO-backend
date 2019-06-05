package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.CostAggregation
import com.github.biancacristina.sgobackend.dto.CostAggregationDTO
import com.github.biancacristina.sgobackend.repositories.CostAggregationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CostAggregationService {

    @Autowired
    private lateinit var costAggregationRepository: CostAggregationRepository

    @Autowired
    private lateinit var typeOfCostAggregationService: TypeOfCostAggregationService

    fun findById(id: Long): CostAggregation {
        return costAggregationRepository.findById(id).orElse(null)

        // Add exception handler for the case when is not find
    }

    fun insert(obj: CostAggregation): CostAggregation {
        obj.id = 0

        return costAggregationRepository.save(obj)
    }

    fun update(
        objDTO: CostAggregationDTO,
        id: Long
    ) {
        var obj = this.findById(id)
        updateData(objDTO, obj)
        costAggregationRepository.save(obj)
    }

    protected fun updateData(
        objDTO: CostAggregationDTO,
        obj: CostAggregation
    ) {
        if (objDTO.name != "") obj.name = objDTO.name

        // Add exception handler for the case when the name is empty
    }

    fun deleteById(id: Long) {
        this.findById(id)
        costAggregationRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: CostAggregationDTO): CostAggregation {
        var typeOfCostAggregation = typeOfCostAggregationService
                                    .findById(objDTO.id_typeOfCostAggregation)

        var obj = CostAggregation(
                0,
                objDTO.name,
                typeOfCostAggregation
        )

        return obj
    }
}