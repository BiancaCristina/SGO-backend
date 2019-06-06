package com.github.biancacristina.sgobackend.dto

data class LaborUpdateDTO (
    var id: Long,
    var id_cluster: Long?,
    var id_typeOfLabor: Long?,
    var id_costAggregation: Long?,
    var id_project: Long?
)