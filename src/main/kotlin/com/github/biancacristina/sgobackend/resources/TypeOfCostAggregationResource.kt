package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.TypeOfCostAggregationDTO
import com.github.biancacristina.sgobackend.services.TypeOfCostAggregationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/typesOfCostAggregation"])
class TypeOfCostAggregationResource {

    @Autowired
    private lateinit var typeOfCostAggregationService: TypeOfCostAggregationService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = typeOfCostAggregationService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(method=[RequestMethod.POST])
    fun insert(
        @Valid @RequestBody objDTO: TypeOfCostAggregationDTO
    ): ResponseEntity<Unit> {
        var obj = typeOfCostAggregationService.fromDTO(objDTO)
        obj = typeOfCostAggregationService.insert(obj)

        // Create the URI of the object inserted
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id).toUri()

        return ResponseEntity.created(uri).build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.PUT])
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: TypeOfCostAggregationDTO
    ): ResponseEntity<Unit> {
        typeOfCostAggregationService.update(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        typeOfCostAggregationService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}