package com.github.biancacristina.sgobackend.dto

data class ProjectNewDTO (
    var id: Long,
    var estimate_service: Double?,
    var estimate_infra: Double?,
    var estimate_material: Double?,
    var estimate_eletronic: Double?,
    var estimate_others: Double?,
    var estimate_startDate: String?,
    var estimate_endDate: String?,
    var id_city: Long?,
    var ids_labors: MutableSet<Long>?
) {
    init {
        // Indicates that if the value is null, so the value is 0
        estimate_service = estimate_service?:0.0
        estimate_infra = estimate_infra?:0.0
        estimate_material = estimate_material?:0.0
        estimate_eletronic = estimate_eletronic?:0.0
        estimate_others = estimate_others?:0.0
    }
}