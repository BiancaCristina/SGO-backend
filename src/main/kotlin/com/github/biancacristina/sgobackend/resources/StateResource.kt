package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.StateDTO
import com.github.biancacristina.sgobackend.services.StateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/states"])
class StateResource {

    @Autowired
    private lateinit var stateService: StateService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = stateService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(method=[RequestMethod.POST])
    fun insert(@Valid @RequestBody objDTO: StateDTO): ResponseEntity<Unit> {
        var obj = stateService.fromDTO(objDTO)
        obj = stateService.insert(obj)

        // Create the URI of the object inserted
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id).toUri()

        return ResponseEntity.created(uri).build()
    }

    @RequestMapping(value=["/updateName/{id}"], method=[RequestMethod.PUT])
    fun updateName(
            @PathVariable id: Long,
            @Valid @RequestBody objDTO: StateDTO): ResponseEntity<Unit> {
        stateService.updateName(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        stateService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}