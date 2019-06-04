package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.ClusterNewDTO
import com.github.biancacristina.sgobackend.services.ClusterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value=["/clusters"])
class ClusterResource {

    @Autowired
    private lateinit var clusterService: ClusterService

    @RequestMapping(value=["/{id}"], method=[RequestMethod.GET])
    fun findById(@PathVariable id: Long): ResponseEntity<*> {
        var obj = clusterService.findById(id)

        return ResponseEntity.ok().body(obj)
    }

    @RequestMapping(value=["/{idCity}"], method=[RequestMethod.POST])
    fun insert(
        @PathVariable idCity: Long,
        @Valid @RequestBody newObjDTO: ClusterNewDTO
    ): ResponseEntity<Unit> {
        newObjDTO.id_city = idCity
        var obj = clusterService.fromDTO(newObjDTO)
        obj = clusterService.insert(obj)

        // Create the URI of the object inserted
        val uri1 = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id)
                .toUri()

        var uriString = uri1.toString()

        val str = uriString.split("clusters/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        uriString = str[0] + "clusters/" + obj.id

        val uri2 = ServletUriComponentsBuilder
                .fromUriString(uriString)
                .buildAndExpand(obj.id)
                .toUri()

        return ResponseEntity.created(uri2).build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.PUT])
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody newObjDTO: ClusterNewDTO
    ): ResponseEntity<Unit> {
        clusterService.update(newObjDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        clusterService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}