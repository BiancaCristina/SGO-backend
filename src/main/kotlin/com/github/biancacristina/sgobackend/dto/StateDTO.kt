package com.github.biancacristina.sgobackend.dto

import com.github.biancacristina.sgobackend.services.validations.NotEmptyField

data class StateDTO (
    var id: Long,

    @field: NotEmptyField("O nome do estado não pode estar vazio.")
    var name: String
)