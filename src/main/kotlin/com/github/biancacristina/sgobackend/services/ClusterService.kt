package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.Cluster
import com.github.biancacristina.sgobackend.dto.ClusterNewDTO
import com.github.biancacristina.sgobackend.repositories.ClusterRepository
import com.github.biancacristina.sgobackend.services.exceptions.DataIntegrityException
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ClusterService {

    @Autowired
    private lateinit var clusterRepository: ClusterRepository

    @Autowired
    private lateinit var cityService: CityService

    fun findById(id: Long): Cluster {
        var obj = clusterRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Cluster não encontrado.")
        }
    }

    fun findAllPaged(
            page: Int,
            linesPerPage: Int,
            direction: String,
            orderBy: String
    ): Page<Cluster> {
        var pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        )

        return clusterRepository.findAll(pageRequest)
    }

    fun insert(obj: Cluster): Cluster {
        obj.id = 0

        return clusterRepository.save(obj)
    }

    fun updateName(
            newObjDTO: ClusterNewDTO,
            id: Long) {
        var obj = this.findById(id)

        if (newObjDTO.name != "") obj.name = newObjDTO.name?:obj.name

        clusterRepository.save(obj)
    }

    fun updateCity(
        objDTO: ClusterNewDTO,
        id: Long
    ) {
        var obj = this.findById(id)
        var city = cityService.findById(objDTO.id_city!!)
        obj.city = city

        clusterRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)

        try {
            clusterRepository.deleteById(id)
        }

        catch(e: DataIntegrityViolationException) {
            throw DataIntegrityException("Não é possível excluir um cluster que possui obras associadas.")
        }
    }

    fun fromDTO(newObjDTO: ClusterNewDTO): Cluster {
        var city = cityService.findById(newObjDTO.id_city!!)
        var obj = Cluster(newObjDTO.id, newObjDTO.name!!, city)

        return obj
    }
}