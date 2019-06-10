package com.github.biancacristina.sgobackend.dto

import com.github.biancacristina.sgobackend.services.validations.NotEmptyField

data class CityNewDTO (
    var id: Long,

    @field: NotEmptyField("O nome da cidade não pode estar vazio.")
    var name: String,

    var id_state: Long
)