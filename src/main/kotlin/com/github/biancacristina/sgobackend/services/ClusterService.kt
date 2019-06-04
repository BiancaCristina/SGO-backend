package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.Cluster
import com.github.biancacristina.sgobackend.dto.ClusterNewDTO
import com.github.biancacristina.sgobackend.repositories.ClusterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ClusterService {

    @Autowired
    private lateinit var clusterRepository: ClusterRepository

    @Autowired
    private lateinit var cityService: CityService

    fun findById(id: Long): Cluster {
        return clusterRepository.findById(id).orElse(null)
    }

    fun insert(obj: Cluster): Cluster {
        obj.id = 0

        return clusterRepository.save(obj)
    }

    fun update(newObjDTO: ClusterNewDTO, id: Long) {
        var obj = this.findById(id)
        updateData(newObjDTO, obj)

        clusterRepository.save(obj)
    }

    protected fun updateData(newObjDTO: ClusterNewDTO, obj: Cluster) {
        if (newObjDTO.name != "") {
            obj.name = newObjDTO.name
        }

        // Add exception handler when the new name is empty
    }

    fun deleteById(id: Long) {
        this.findById(id)
        clusterRepository.deleteById(id)

        // Add exception handler when the deletion is not possible
    }

    fun fromDTO(newObjDTO: ClusterNewDTO): Cluster {
        var city = cityService.findById(newObjDTO.id_city)
        var obj = Cluster(newObjDTO.id, newObjDTO.name, city)

        return obj
    }
}