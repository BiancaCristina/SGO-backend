package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.domain.Cluster
import com.github.biancacristina.sgobackend.dto.ClusterDTO
import com.github.biancacristina.sgobackend.dto.ClusterNewDTO
import com.github.biancacristina.sgobackend.services.ClusterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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

    @RequestMapping(value=["/page"], method=[RequestMethod.GET])
    fun findAllPage(
            @RequestParam(value="page", defaultValue= "0") page: Int,
            @RequestParam(value="linesPerPage", defaultValue= "10") linesPerPage: Int,
            @RequestParam(value="direction", defaultValue= "DESC") direction: String,
            @RequestParam(value="orderBy", defaultValue= "name") orderBy: String
    ): ResponseEntity<Page<ClusterDTO>> {
        var listPaged: Page<Cluster> = clusterService.findAllPaged(page, linesPerPage, direction, orderBy)
        var listPagedDTO: Page<ClusterDTO> = listPaged.map { obj -> ClusterDTO(obj.id, obj.name) }

        return ResponseEntity.ok().body(listPagedDTO)
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

    @RequestMapping(value=["/updateName/{id}"], method=[RequestMethod.PUT])
    fun updateName(
        @PathVariable id: Long,
        @Valid @RequestBody newObjDTO: ClusterNewDTO
    ): ResponseEntity<Unit> {
        clusterService.updateName(newObjDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/updateCity/{id}"], method=[RequestMethod.PUT])
    fun updateCity(
        @PathVariable id: Long,
        @Valid @RequestBody newObjDTO: ClusterNewDTO
    ): ResponseEntity<Unit> {
        clusterService.updateCity(newObjDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        clusterService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}