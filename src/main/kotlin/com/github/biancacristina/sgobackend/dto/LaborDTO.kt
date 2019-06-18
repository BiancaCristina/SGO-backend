package com.github.biancacristina.sgobackend.dto

data class LaborDTO (
    var id: Long,
    var name: String,
    var estimate_service: Double?,
    var estimate_infra: Double?,
    var estimate_material: Double?,
    var estimate_eletronic: Double?,
    var estimate_others: Double?
)