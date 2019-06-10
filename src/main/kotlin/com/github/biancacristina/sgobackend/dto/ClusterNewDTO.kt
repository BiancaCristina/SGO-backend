package com.github.biancacristina.sgobackend.dto

import com.github.biancacristina.sgobackend.services.validations.NotEmptyField

data class ClusterNewDTO(
    var id: Long,

    @field: NotEmptyField("O nome do cluster não pode estar vazio.")
    var name: String?,

    var id_city: Long?
)