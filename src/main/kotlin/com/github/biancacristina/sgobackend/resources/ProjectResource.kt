package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.domain.Project
import com.github.biancacristina.sgobackend.dto.LaborNewDTO
import com.github.biancacristina.sgobackend.dto.ProjectDTO
import com.github.biancacristina.sgobackend.dto.ProjectNewDTO
import com.github.biancacristina.sgobackend.services.ProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/projects"])
class ProjectResource {

    @Autowired
    private lateinit var projectService: ProjectService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = projectService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(value=["/page"], method=[RequestMethod.GET])
    fun findAllPage(
            @RequestParam(value="page", defaultValue= "0") page: Int,
            @RequestParam(value="linesPerPage", defaultValue= "10") linesPerPage: Int,
            @RequestParam(value="direction", defaultValue= "DESC") direction: String,
            @RequestParam(value="orderBy", defaultValue= "id") orderBy: String
    ): ResponseEntity<Page<ProjectDTO>> {
        var listPaged: Page<Project> = projectService.findAllPaged(page, linesPerPage, direction, orderBy)
        var listPagedDTO: Page<ProjectDTO> =
                listPaged.map { obj -> ProjectDTO(
                        obj.id,
                        obj.estimate_startDate,
                        obj.estimate_endDate,
                        obj.status.status) }

        return ResponseEntity.ok().body(listPagedDTO)
    }

    @RequestMapping(method=[RequestMethod.POST])
    fun insert(@Valid @RequestBody objDTO: ProjectNewDTO): ResponseEntity<Unit> {
        var obj = projectService.insert(objDTO)

        // Create the URI of the object inserted
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id).toUri()

        return ResponseEntity.created(uri).build()
    }

    @RequestMapping(value=["/updateEstimate/{id}"], method=[RequestMethod.PUT])
    fun updateEstimate(
            @PathVariable id: Long,
            @Valid @RequestBody objDTO: ProjectNewDTO
    ): ResponseEntity<Unit> {
        projectService.updateEstimate(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/updateDate/{id}"], method=[RequestMethod.PUT])
    fun updateDate(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: ProjectNewDTO
    ): ResponseEntity<Unit> {
        projectService.updateDate(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/updateCity/{idProject}/{idCity}"], method=[RequestMethod.PUT])
    fun updateCity(
        @PathVariable idProject: Long,
        @PathVariable idCity: Long
    ): ResponseEntity<Unit> {
        projectService.updateCity(idProject, idCity)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/updateAddLabor/{id}"], method=[RequestMethod.PUT])
    fun updateAddLabor(
        @PathVariable id: Long,
        @Valid @RequestBody laborNewDTO: LaborNewDTO
    ): ResponseEntity<Unit> {
        laborNewDTO.id_project = id
        projectService.updateAddLabor(id, laborNewDTO)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/updateRemoveLabor/{idProject}/{idLabor}"], method=[RequestMethod.PUT])
    fun updateRemoveLabor (
        @PathVariable idProject: Long,
        @PathVariable idLabor: Long
    ): ResponseEntity<Unit> {
        projectService.updateRemoveLabor(idProject, idLabor)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        projectService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}