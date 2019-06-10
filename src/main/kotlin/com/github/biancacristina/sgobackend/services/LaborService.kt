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

    fun updateEstimate(objDTO: LaborDTO, id: Long) {
        var obj = this.findById(id)

        obj.estimate_service = objDTO.estimate_service?:obj.estimate_service
        obj.estimate_infra = objDTO.estimate_infra?:obj.estimate_infra
        obj.estimate_material = objDTO.estimate_material?:obj.estimate_material
        obj.estimate_eletronic = objDTO.estimate_eletronic?:obj.estimate_eletronic
        obj.estimate_others = objDTO.estimate_others?:obj.estimate_others

        // Add exception handler for the case when is null

        // Update estimate_total
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_service?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_infra?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_material?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_eletronic?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_others?:0.0)

        laborRepository.save(obj)
    }

    fun updateCluster(
        idCluster: Long,
        idLabor: Long
    ) {
        var obj = this.findById(idLabor)
        var cluster = clusterService.findById(idCluster)

        obj.cluster = cluster

        laborRepository.save(obj)
    }

    fun updateTypeOfLabor(
        idTypeOfLabor: Long,
        idLabor: Long
    ) {
        var obj = this.findById(idLabor)
        var typeOfLabor = typeOfLaborService.findById(idTypeOfLabor)

        obj.typeOfLabor = typeOfLabor

        laborRepository.save(obj)
    }

    fun updateCostAggregation(
        idCostAggregation: Long,
        idLabor: Long
    ) {
        var obj = this.findById(idLabor)
        var costAggregation = costAggregationService.findById(idCostAggregation)

        obj.costAggregation = costAggregation

        laborRepository.save(obj)
    }

    fun updateProject(
        idProject: Long,
        idLabor: Long
    ) {
        var obj = this.findById(idLabor)
        var project = projectService.findById(idProject)

        obj.project = project

        laborRepository.save(obj)
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
        var project = projectService.findById(objDTO.id_project!!)

        System.out.println(typeOfLabor.name)
        var obj = Labor(
                objDTO.id,
                objDTO.estimate_service?:0.0,
                objDTO.estimate_infra?:0.0,
                objDTO.estimate_material?:0.0,
                objDTO.estimate_eletronic?:0.0,
                objDTO.estimate_others?:0.0,
                cluster,
                typeOfLabor,
                costAggregation,
                project
        )

        // Add exception handler for the case when objDTO has something NULL

        return obj
    }
}