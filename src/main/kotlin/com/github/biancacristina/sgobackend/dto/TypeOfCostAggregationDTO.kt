package com.github.biancacristina.sgobackend.dto

import com.github.biancacristina.sgobackend.services.validations.NotEmptyField

data class TypeOfCostAggregationDTO (
    var id: Long,

    @field: NotEmptyField("O nome do tipo de agregador de custo não pode estar vazio.")
    var name: String
)