package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.City
import com.github.biancacristina.sgobackend.dto.CityNewDTO
import com.github.biancacristina.sgobackend.repositories.CityRepository
import com.github.biancacristina.sgobackend.services.exceptions.DataIntegrityException
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
                "Cidade não encontrada.")
        }
    }

    fun findAllPaged(
            page: Int,
            linesPerPage: Int,
            direction: String,
            orderBy: String
    ): Page<City> {
        var pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy
        )

        return cityRepository.findAll(pageRequest)
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

        try {
            cityRepository.deleteById(id)
        }

        catch(e: DataIntegrityViolationException) {
            throw DataIntegrityException("Não é possível excluir uma cidade que possui clusters ou projetos associados.")
        }
    }

    fun fromDTO(newObjDTO: CityNewDTO): City {
        var state = stateService.findById(newObjDTO.id_state)
        var obj = City(newObjDTO.id, newObjDTO.name, state)

        return obj
    }
}