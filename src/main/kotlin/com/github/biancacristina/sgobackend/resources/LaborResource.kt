package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.LaborDTO
import com.github.biancacristina.sgobackend.services.LaborService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/labors"])
class LaborResource {

    @Autowired
    private lateinit var laborService: LaborService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = laborService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(value=["/{idCluster}/{idTypeOfLabor}"], method=[RequestMethod.POST])
    fun insert(
        @PathVariable idCluster: Long,
        @PathVariable idTypeOfLabor: Long,
        @Valid @RequestBody objDTO: LaborDTO
    ): ResponseEntity<Unit> {

        objDTO.id_cluster = idCluster
        objDTO.id_typeOfLabor = idTypeOfLabor

        var obj = laborService.fromDTO(objDTO)
        obj = laborService.insert(obj)

        // Create the URI of the object inserted
        val uri1 = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id)
                .toUri()

        var uriString = uri1.toString()

        val str = uriString.split("labors/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        uriString = str[0] + "labors/" + obj.id

        val uri2 = ServletUriComponentsBuilder
                .fromUriString(uriString)
                .buildAndExpand(obj.id)
                .toUri()

        return ResponseEntity.created(uri2).build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.PUT])
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: LaborDTO
    ): ResponseEntity<Unit> {
        laborService.update(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        laborService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}