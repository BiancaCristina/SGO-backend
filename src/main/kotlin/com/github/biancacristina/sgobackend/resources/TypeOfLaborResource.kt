package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.domain.TypeOfLabor
import com.github.biancacristina.sgobackend.dto.TypeOfLaborDTO
import com.github.biancacristina.sgobackend.services.TypeOfLaborService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/typesOfLabor"])
class TypeOfLaborResource {

    @Autowired
    private lateinit var typeOfLaborService: TypeOfLaborService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = typeOfLaborService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(value=["/page"], method=[RequestMethod.GET])
    fun findAllPage(
            @RequestParam(value="page", defaultValue= "0") page: Int,
            @RequestParam(value="linesPerPage", defaultValue= "10") linesPerPage: Int,
            @RequestParam(value="direction", defaultValue= "DESC") direction: String,
            @RequestParam(value="orderBy", defaultValue= "name") orderBy: String
    ): ResponseEntity<Page<TypeOfLaborDTO>> {
        var listPaged: Page<TypeOfLabor> = typeOfLaborService.findAllPaged(page, linesPerPage, direction, orderBy)
        var listPagedDTO: Page<TypeOfLaborDTO> =
                listPaged.map { obj -> TypeOfLaborDTO(obj.id, obj.name) }

        return ResponseEntity.ok().body(listPagedDTO)
    }

    @RequestMapping(method=[RequestMethod.POST])
    fun insert(
            @Valid @RequestBody objDTO: TypeOfLaborDTO
    ): ResponseEntity<Unit> {
        var obj = typeOfLaborService.fromDTO(objDTO)
        obj = typeOfLaborService.insert(obj)

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
        @RequestBody objDTO: TypeOfLaborDTO
    ): ResponseEntity<Unit> {
        typeOfLaborService.updateName(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"],method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        typeOfLaborService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}