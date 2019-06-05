package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.services.ProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value=["/projects"])
class ProjectResource {

    @Autowired
    private lateinit var projectService: ProjectService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        // Not tested yet
        var obj = projectService.findById(id)

        return ResponseEntity.ok().body(obj)
    }
}