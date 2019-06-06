package com.github.biancacristina.sgobackend.dto

data class ProjectNewDTO (
    var id: Long,
    var estimate_service: Double?,
    var estimate_infra: Double?,
    var estimate_material: Double?,
    var estimate_eletronic: Double?,
    var estimate_others: Double?,
    var estimate_startDate: String,
    var estimate_endDate: String,
    var id_city: Long,
    var ids_labors: MutableSet<Long>
)