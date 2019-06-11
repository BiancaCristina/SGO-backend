package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.TypeOfLabor
import com.github.biancacristina.sgobackend.dto.TypeOfLaborDTO
import com.github.biancacristina.sgobackend.repositories.TypeOfLaborRepository
import com.github.biancacristina.sgobackend.services.exceptions.DataIntegrityException
import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class TypeOfLaborService {

    @Autowired
    private lateinit var typeOfLaborRepository: TypeOfLaborRepository

    fun findById(id: Long): TypeOfLabor {
        var obj = typeOfLaborRepository.findById(id)

        return obj.orElseThrow { ObjectNotFoundException(
                "Tipo de obra não encontrado!")
        }
    }

    fun insert(obj: TypeOfLabor): TypeOfLabor {
        obj.id = 0

        return typeOfLaborRepository.save(obj)
    }

    fun updateName(
            objDTO: TypeOfLaborDTO,
            id: Long) {
        var obj = this.findById(id)

        if (objDTO.name != "") obj.name = objDTO.name

        typeOfLaborRepository.save(obj)
    }

    fun deleteById(id: Long) {
        this.findById(id)

        try {
            typeOfLaborRepository.deleteById(id)
        }

        catch(e: DataIntegrityViolationException) {
            throw DataIntegrityException("Não é possível excluir tipo de obra que possui obras associadas.")
        }
    }

    fun fromDTO(objDTO: TypeOfLaborDTO): TypeOfLabor {
        var obj = TypeOfLabor(objDTO.id, objDTO.name)
        return obj
    }


}