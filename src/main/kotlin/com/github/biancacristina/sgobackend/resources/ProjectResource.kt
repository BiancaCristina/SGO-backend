package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.LaborDTO
import com.github.biancacristina.sgobackend.dto.ProjectNewDTO
import com.github.biancacristina.sgobackend.services.ProjectService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
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
        @Valid @RequestBody laborDTO: LaborDTO
    ): ResponseEntity<Unit> {
        laborDTO.id_project = id
        projectService.updateAddLabor(id, laborDTO)

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