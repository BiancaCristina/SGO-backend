package com.github.biancacristina.sgobackend.resources

import com.github.biancacristina.sgobackend.dto.LaborDTO
import com.github.biancacristina.sgobackend.dto.LaborUpdateDTO
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

    @RequestMapping(value=["/{idCluster}/{idTypeOfLabor}/{idCostAggregation}"], method=[RequestMethod.POST])
    fun insert(
        @PathVariable idCluster: Long,
        @PathVariable idTypeOfLabor: Long,
        @PathVariable idCostAggregation: Long,
        @Valid @RequestBody objDTO: LaborDTO
    ): ResponseEntity<Unit> {

        objDTO.id_cluster = idCluster
        objDTO.id_typeOfLabor = idTypeOfLabor
        objDTO.id_costAggregation = idCostAggregation

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

    @RequestMapping(value=["/updateEstimate/{id}"], method=[RequestMethod.PUT])
    fun updateEstimate(
        @PathVariable id: Long,
        @Valid @RequestBody objDTO: LaborDTO
    ): ResponseEntity<Unit> {
        laborService.updateEstimate(objDTO, id)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(
            value=["/updateCluster/{idLabor}/{idCluster}"],
            method=[RequestMethod.PUT])
    fun updateCluster(
        @PathVariable idLabor: Long,
        @PathVariable idCluster: Long
    ): ResponseEntity<Unit> {
        laborService.updateCluster(idCluster, idLabor)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(
            value=["/updateTypeOfLabor/{idLabor}/{idTypeOfLabor}"],
            method=[RequestMethod.PUT])
    fun updateTypeOfLabor(
        @PathVariable idLabor: Long,
        @PathVariable idTypeOfLabor: Long
    ): ResponseEntity<Unit>{
        laborService.updateTypeOfLabor(idTypeOfLabor, idLabor)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(
            value=["/updateCostAggregation/{idLabor}/{idCostAggregation}"],
            method=[RequestMethod.PUT]
    )
    fun updateCostAggregation(
        @PathVariable idLabor: Long,
        @PathVariable idCostAggregation: Long
    ): ResponseEntity<Unit> {
        laborService.updateCostAggregation(idCostAggregation, idLabor)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(
            value=["/updateProject/{idLabor}/{idProject}"],
            method=[RequestMethod.PUT]
    )
    fun updateProject(
        @PathVariable idLabor: Long,
        @PathVariable idProject: Long
    ): ResponseEntity<Unit> {
        laborService.updateProject(idProject, idLabor)

        return ResponseEntity.noContent().build()
    }

    @RequestMapping(value=["/{id}"], method=[RequestMethod.DELETE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        laborService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}