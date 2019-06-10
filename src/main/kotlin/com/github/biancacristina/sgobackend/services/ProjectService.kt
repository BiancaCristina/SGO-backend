package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.Project
import com.github.biancacristina.sgobackend.domain.enums.Status
import com.github.biancacristina.sgobackend.dto.ProjectNewDTO
import com.github.biancacristina.sgobackend.repositories.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    fun insert(objDTO: ProjectNewDTO): Project {
        var obj = fromDTO(objDTO)
        obj.id = 0
        obj.status = Status.ACIONADO

        projectRepository.save(obj)

        // Create the labors from objDTO.laborsDTO
        for (laborDTO in objDTO.laborsDTO!!) {
            laborDTO.id_project = obj.id
            var labor = laborService.fromDTO(laborDTO)
            laborService.insert(labor)
            obj.labors.add(labor)
        }

        return projectRepository.save(obj)
    }

    fun updateEstimate(
            objDTO: ProjectNewDTO,
            id: Long) {
        var obj = this.findById(id)
        updateEstimate(objDTO, obj)

        projectRepository.save(obj)
    }

    protected fun updateEstimate(
            objDTO: ProjectNewDTO,
            obj: Project) {
        // Update the estimates

        obj.estimate_service = objDTO.estimate_service
        obj.estimate_infra = objDTO.estimate_infra
        obj.estimate_material = objDTO.estimate_material
        obj.estimate_eletronic = objDTO.estimate_eletronic
        obj.estimate_others = objDTO.estimate_others

        // Update the total
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_service?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_infra?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_material?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_eletronic?:0.0)
        obj.estimate_total = obj.estimate_total?.plus(obj.estimate_others?:0.0)
    }

    fun deleteById(id: Long) {
        this.findById(id)
        projectRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: ProjectNewDTO): Project {
        // Conversion used only for insertion
        // Also create the labors from the LaborDTO

        var city = cityService.findById(objDTO.id_city!!)

        // Create the dates
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        var dateStart = LocalDateTime.parse(objDTO.estimate_startDate, formatter)
        var dateEnd = LocalDateTime.parse(objDTO.estimate_endDate, formatter)

        var obj = Project(
                0,
                objDTO.estimate_service,
                objDTO.estimate_infra,
                objDTO.estimate_material,
                objDTO.estimate_eletronic,
                objDTO.estimate_others,
                dateStart,
                dateEnd,
                city,
                Status.INDEFINIDO
        )

        // Add exception handler to deal with the parts of this process

        return obj
    }
}