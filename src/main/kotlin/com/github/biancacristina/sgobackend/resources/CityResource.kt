package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.domain.State
import com.github.biancacristina.sgobackend.dto.CityDTO
import com.github.biancacristina.sgobackend.dto.CityNewDTO
import com.github.biancacristina.sgobackend.services.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/cities"])
class CityResource {

    @Autowired
    private lateinit var cityService: CityService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = cityService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(value=["/page"], method=[RequestMethod.GET])
    fun findAllPage(
            @RequestParam(value="page", defaultValue= "0") page: Int,
            @RequestParam(value="linesPerPage", defaultValue= "10") linesPerPage: Int,
            @RequestParam(value="direction", defaultValue= "DESC") direction: String,
            @RequestParam(value="orderBy", defaultValue= "name") orderBy: String
    ): ResponseEntity<Page<CityDTO>> {
        var listPaged: Page<City> = cityService.findAllPaged(page, linesPerPage, direction, orderBy)
        var listPagedDTO: Page<CityDTO> = listPaged.map { obj -> CityDTO(obj.id, obj.name) }

        return ResponseEntity.ok().body(listPagedDTO)
    }

    @RequestMapping(value=["/{idState}"], method=[RequestMethod.POST])
    fun insert(
            @PathVariable idState: Long,
            @Valid @RequestBody newObjDTO: CityNewDTO
    ): ResponseEntity<Unit> {
        newObjDTO.id_state = idState
        var obj = cityService.fromDTO(newObjDTO)
        obj = cityService.insert(obj)

        // Create the URI of the object inserted
        val uri1 = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id)
                .toUri()

        var uriString = uri1.toString()

        val str = uriString.split("cities/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        uriString = str[0] + "cities/" + obj.id

        val uri2 = ServletUriComponentsBuilder
                .fromUriString(uriString)
                .buildAndExpand(obj.id)
                .toUri()

        return ResponseEntity.created(uri2).build()
    }

    @RequestMapping(value=["/updateName/{id}"], method=[RequestMethod.PUT])
    fun updateName(
        @PathVariable id: Long,
        @Valid @RequestBody newObjDTO: CityNewDTO
    ): ResponseEntity<Unit> {
        cityService.updateName(newObjDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        cityService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}