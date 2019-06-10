package com.github.biancacristina.sgobackend.dto

import com.github.biancacristina.sgobackend.services.validations.NotEmptyField

data class CostAggregationDTO (
    var id: Long,

    @field: NotEmptyField("O nome do agregador de custo não pode estar vazio.")
    var name: String,

    var id_typeOfCostAggregation: Long?
)