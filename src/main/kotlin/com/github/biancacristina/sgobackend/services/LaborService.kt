package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.Labor
import com.github.biancacristina.sgobackend.dto.LaborDTO
import com.github.biancacristina.sgobackend.dto.LaborUpdateDTO
import com.github.biancacristina.sgobackend.repositories.LaborRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LaborService {

    @Autowired
    private lateinit var laborRepository: LaborRepository

    @Autowired
    private lateinit var typeOfLaborService: TypeOfLaborService

    @Autowired
    private lateinit var clusterService: ClusterService

    @Autowired
    private lateinit var costAggregationService: CostAggregationService

    @Autowired
    private lateinit var projectService: ProjectService

    fun findById(id: Long): Labor {
        return laborRepository.findById(id).orElse(null)
    }

    fun insert(obj: Labor): Labor {
        obj.id = 0

        return laborRepository.save(obj)
    }

    fun update(objDTO: LaborDTO, id: Long) {
        var obj = this.findById(id)
        updateData(objDTO, obj)

        laborRepository.save(obj)
    }

    fun update(objDTO: LaborUpdateDTO, id: Long) {
        var obj = this.findById(id)
        updateProject(objDTO, obj)

        laborRepository.save(obj)
    }

    protected fun updateData(
        objDTO: LaborDTO,
        obj: Labor) {

        obj.estimate_service = objDTO.estimate_service?:obj.estimate_service
        obj.estimate_infra = objDTO.estimate_infra?:obj.estimate_infra
        obj.estimate_material = objDTO.estimate_material?:obj.estimate_material
        obj.estimate_eletronic = objDTO.estimate_eletronic?:obj.estimate_eletronic
        obj.estimate_others = objDTO.estimate_others?:obj.estimate_others

        // Add exception handler for the case when is null
    }

    protected fun updateProject(
        objDTO: LaborUpdateDTO,
        obj: Labor
    ) {
        var project = projectService.findById(objDTO.id_project!!)

        obj.project = project
    }

    fun deleteById(id: Long) {
        this.findById(id)
        laborRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: LaborDTO): Labor {
        var typeOfLabor = typeOfLaborService.findById(objDTO.id_typeOfLabor)
        var cluster = clusterService.findById(objDTO.id_cluster)
        var costAggregation = costAggregationService.findById(objDTO.id_costAggregation)

        System.out.println(typeOfLabor.name)
        var obj = Labor(
                objDTO.id,
                objDTO.estimate_service!!,
                objDTO.estimate_infra!!,
                objDTO.estimate_material!!,
                objDTO.estimate_eletronic!!,
                objDTO.estimate_others!!,
                cluster,
                typeOfLabor,
                costAggregation
        )

        // Add exception handler for the case when objDTO has something NULL

        return obj
    }
}