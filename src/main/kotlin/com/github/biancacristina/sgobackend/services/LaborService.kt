package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.Labor
import com.github.biancacristina.sgobackend.dto.LaborNewDTO
import com.github.biancacristina.sgobackend.repositories.LaborRepository
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
        var obj = laborRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Obra não encontrada!")
        }
    }

    fun findAllPaged(
            page: Int,
            linesPerPage: Int,
            direction: String,
            orderBy: String
    ): Page<Labor> {
        var pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        )

        return laborRepository.findAll(pageRequest)
    }

    fun insert(obj: Labor): Labor {
        obj.id = 0

        return laborRepository.save(obj)
    }

    fun updateEstimate(objNewDTO: LaborNewDTO, id: Long) {
        var obj = this.findById(id)

        obj.estimate_service = objNewDTO.estimate_service?:obj.estimate_service
        obj.estimate_infra = objNewDTO.estimate_infra?:obj.estimate_infra
        obj.estimate_material = objNewDTO.estimate_material?:obj.estimate_material
        obj.estimate_eletronic = objNewDTO.estimate_eletronic?:obj.estimate_eletronic
        obj.estimate_others = objNewDTO.estimate_others?:obj.estimate_others

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

    fun fromDTO(objNewDTO: LaborNewDTO): Labor {
        var typeOfLabor = typeOfLaborService.findById(objNewDTO.id_typeOfLabor)
        var cluster = clusterService.findById(objNewDTO.id_cluster)
        var costAggregation = costAggregationService.findById(objNewDTO.id_costAggregation)
        var project = projectService.findById(objNewDTO.id_project!!)

        System.out.println(typeOfLabor.name)
        var obj = Labor(
                objNewDTO.id,
                objNewDTO.estimate_service?:0.0,
                objNewDTO.estimate_infra?:0.0,
                objNewDTO.estimate_material?:0.0,
                objNewDTO.estimate_eletronic?:0.0,
                objNewDTO.estimate_others?:0.0,
                cluster,
                typeOfLabor,
                costAggregation,
                project
        )

        // Add exception handler for the case when objNewDTO has something NULL

        return obj
    }
}