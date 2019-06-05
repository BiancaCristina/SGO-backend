package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.Labor
import com.github.biancacristina.sgobackend.domain.Project
import com.github.biancacristina.sgobackend.domain.enums.Status
import com.github.biancacristina.sgobackend.dto.ProjectNewDTO
import com.github.biancacristina.sgobackend.repositories.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectService {

    @Autowired
    private lateinit var projectRepository: ProjectRepository

    @Autowired
    private lateinit var cityService: CityService

    @Autowired
    private lateinit var laborService: LaborService

    fun findById(id: Long): Project {
        return projectRepository.findById(id).orElse(null)
    }

    fun insert(obj: Project): Project {
        obj.id = 0
        obj.status = Status.ACIONADO

        return projectRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)
        projectRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: ProjectNewDTO): Project {
        // Conversion used only for insertion

        var city = cityService.findById(objDTO.id_city)
        var labors = mutableSetOf<Labor>()

        for (id_labor in objDTO.ids_labors) {
            // Create a mutableSet of Labor using the ids_labors

            var labor = laborService.findById(id_labor)

            // Add here the update of labor to include the project

            labors.add(labor)
        }

        var obj = Project(
                0,
                objDTO.estimate_service,
                objDTO.estimate_infra,
                objDTO.estimate_material,
                objDTO.estimate_eletronic,
                objDTO.estimate_others,
                objDTO.estimate_startDate,
                objDTO.estimate_endDate,
                city,
                labors,
                Status.INDEFINIDO
        )

        // Add exception handler to deal with the parts of this process

        return obj
    }
}