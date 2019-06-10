package com.github.biancacristina.sgobackend.services

import com.github.biancacristina.sgobackend.domain.TypeOfLabor
import com.github.biancacristina.sgobackend.dto.TypeOfLaborDTO
import com.github.biancacristina.sgobackend.repositories.TypeOfLaborRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TypeOfLaborService {

    @Autowired
    private lateinit var typeOfLaborRepository: TypeOfLaborRepository

    fun findById(id: Long): TypeOfLabor {
        return typeOfLaborRepository.findById(id).orElse(null)
    }

    fun insert(obj: TypeOfLabor): TypeOfLabor {
        obj.id = 0

        return typeOfLaborRepository.save(obj)
    }

    fun updateName(
            objDTO: TypeOfLaborDTO,
            id: Long) {
        var obj = this.findById(id)
        updateName(objDTO, obj)

        typeOfLaborRepository.save(obj)
    }

    protected fun updateName(
        objDTO: TypeOfLaborDTO,
        obj: TypeOfLabor
    ) {
        if (objDTO.name != "") obj.name = objDTO.name

        // Add exception handler for the case when the name is empty
    }

    fun deleteById(id: Long) {
        this.findById(id)
        typeOfLaborRepository.deleteById(id)

        // Add exception handler for the case when the deletion is not possible
    }

    fun fromDTO(objDTO: TypeOfLaborDTO): TypeOfLabor {
        var obj = TypeOfLabor(objDTO.id, objDTO.name)
        return obj
    }


}