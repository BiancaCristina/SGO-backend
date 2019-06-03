package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.dto.CityNewDTO
import com.github.biancacristina.sgobackend.repositories.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService {

    @Autowired
    private lateinit var cityRepository: CityRepository

    @Autowired
    private lateinit var stateService: StateService

    fun findById(id: Long): City {
        return cityRepository.findById(id).orElse(null)
    }

    fun insert(obj: City): City {
        obj.id = 0

        return cityRepository.save(obj)
    }

    fun update(newObjDTO: CityNewDTO, id: Long) {
        var obj = this.findById(id)
        updateData(newObjDTO, obj)

        cityRepository.save(obj)
    }

    protected fun updateData(
            newObjDTO: CityNewDTO,
            obj: City) {

        if (newObjDTO.name != "") obj.name = newObjDTO.name

        // Add exception handler to deal with empty names
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