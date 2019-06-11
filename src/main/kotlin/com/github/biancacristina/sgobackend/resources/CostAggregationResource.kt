package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.domain.CostAggregation
import com.github.biancacristina.sgobackend.dto.CostAggregationDTO
import com.github.biancacristina.sgobackend.dto.CostAggregationNewDTO
import com.github.biancacristina.sgobackend.services.CostAggregationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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

    @RequestMapping(value=["/page"], method=[RequestMethod.GET])
    fun findAllPage(
            @RequestParam(value="page", defaultValue= "0") page: Int,
            @RequestParam(value="linesPerPage", defaultValue= "10") linesPerPage: Int,
            @RequestParam(value="direction", defaultValue= "DESC") direction: String,
            @RequestParam(value="orderBy", defaultValue= "name") orderBy: String
    ): ResponseEntity<Page<CostAggregationDTO>> {
        var listPaged: Page<CostAggregation> = costAggregationService.findAllPaged(page, linesPerPage, direction, orderBy)
        var listPagedDTO: Page<CostAggregationDTO> = listPaged.map { obj -> CostAggregationDTO(obj.id, obj.name) }

        return ResponseEntity.ok().body(listPagedDTO)
    }

    @RequestMapping(value=["/{idTypeOfCostAggregation}"], method=[RequestMethod.POST])
    fun insert(
        @PathVariable idTypeOfCostAggregation: Long,
        @Valid @RequestBody objDTO: CostAggregationNewDTO
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

    @RequestMapping(value=["/updateName/{id}"], method=[RequestMethod.PUT])
    fun updateName(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: CostAggregationNewDTO
    ): ResponseEntity<Unit> {
        costAggregationService.updateName(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(
            value=["/updateTypeOfCostAggregation/{idCostAggregation}/{idTypeOfCostAggregation}"],
            method=[RequestMethod.PUT])
    fun updateTypeOfCostAggregation(
        @PathVariable idCostAggregation: Long,
        @PathVariable idTypeOfCostAggregation: Long
    ): ResponseEntity<Unit> {
        costAggregationService.updateTypeOfCostAggregation(idCostAggregation, idTypeOfCostAggregation)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        costAggregationService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}