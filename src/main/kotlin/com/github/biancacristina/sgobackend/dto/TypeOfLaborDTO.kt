package com.github.biancacristina.sgobackend.dto

import com.github.biancacristina.sgobackend.services.validations.NotEmptyField

data class TypeOfLaborDTO (
    var id: Long,

    @field: NotEmptyField("O nome do tipo de obra não pode estar vazio.")
    var name: String
)