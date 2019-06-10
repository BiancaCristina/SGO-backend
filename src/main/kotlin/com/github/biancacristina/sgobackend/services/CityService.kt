package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.dto.CityNewDTO
import com.github.biancacristina.sgobackend.repositories.CityRepository
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService {

    @Autowired
    private lateinit var cityRepository: CityRepository

    @Autowired
    private lateinit var stateService: StateService

    fun findById(id: Long): City {
        var obj = cityRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Cidade não encontrada!")
        }
    }

    fun insert(obj: City): City {
        obj.id = 0

        return cityRepository.save(obj)
    }

    fun updateName(newObjDTO: CityNewDTO, id: Long) {
        var obj = this.findById(id)

        if (newObjDTO.name != "") obj.name = newObjDTO.name

        cityRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)
        cityRepository.deleteById(id)

        // Add exception handler to deal with the case when the deletion is not possible
    }

    fun fromDTO(newObjDTO: CityNewDTO): City {
        var state = stateService.findById(newObjDTO.id_state)
        var obj = City(newObjDTO.id, newObjDTO.name, state)

        return obj
    }
}