package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.domain.TypeOfCostAggregation
import com.github.biancacristina.sgobackend.dto.TypeOfCostAggregationDTO
import com.github.biancacristina.sgobackend.services.TypeOfCostAggregationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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

    @RequestMapping(value=["/page"], method=[RequestMethod.GET])
    fun findAllPage(
            @RequestParam(value="page", defaultValue= "0") page: Int,
            @RequestParam(value="linesPerPage", defaultValue= "10") linesPerPage: Int,
            @RequestParam(value="direction", defaultValue= "DESC") direction: String,
            @RequestParam(value="orderBy", defaultValue= "name") orderBy: String
    ): ResponseEntity<Page<TypeOfCostAggregationDTO>> {
        var listPaged: Page<TypeOfCostAggregation> = typeOfCostAggregationService.findAllPaged(
                page,
                linesPerPage,
                direction,
                orderBy)

        var listPagedDTO: Page<TypeOfCostAggregationDTO> =
                listPaged.map { obj -> TypeOfCostAggregationDTO(obj.id, obj.name) }

        return ResponseEntity.ok().body(listPagedDTO)
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

    @RequestMapping(value=["/updateName/{id}"], method=[RequestMethod.PUT])
    fun updateName(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: TypeOfCostAggregationDTO
    ): ResponseEntity<Unit> {
        typeOfCostAggregationService.updateName(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        typeOfCostAggregationService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}