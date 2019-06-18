package com.github.biancacristina.sgobackend.dto

data class LaborNewDTO(
        var id: Long,
        var name: String,
        var estimate_service: Double?,
        var estimate_infra: Double?,
        var estimate_material: Double?,
        var estimate_eletronic: Double?,
        var estimate_others: Double?,
        var id_typeOfLabor: Long,
        var id_costAggregation: Long,
        var id_project: Long?
) {
}