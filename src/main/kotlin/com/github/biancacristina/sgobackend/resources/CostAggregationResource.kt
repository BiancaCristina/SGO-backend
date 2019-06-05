package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.CostAggregationDTO
import com.github.biancacristina.sgobackend.services.CostAggregationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/costsAggregation"])
class CostAggregationResource {

    @Autowired
    private lateinit var costAggregationService: CostAggregationService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = costAggregationService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(value=["/{idTypeOfCostAggregation}"], method=[RequestMethod.POST])
    fun insert(
        @PathVariable idTypeOfCostAggregation: Long,
        @Valid @RequestBody objDTO: CostAggregationDTO
    ): ResponseEntity<Unit> {
        objDTO.id_typeOfCostAggregation = idTypeOfCostAggregation

        var obj = costAggregationService.fromDTO(objDTO)
        obj = costAggregationService.insert(obj)

        // Create the URI of the object inserted
        val uri1 = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id)
                .toUri()

        var uriString = uri1.toString()

        val str = uriString.split("costsAggregation/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        uriString = str[0] + "costsAggregation/" + obj.id

        val uri2 = ServletUriComponentsBuilder
                .fromUriString(uriString)
                .buildAndExpand(obj.id)
                .toUri()

        return ResponseEntity.created(uri2).build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.PUT])
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: CostAggregationDTO
    ): ResponseEntity<Unit> {
        costAggregationService.update(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        costAggregationService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}