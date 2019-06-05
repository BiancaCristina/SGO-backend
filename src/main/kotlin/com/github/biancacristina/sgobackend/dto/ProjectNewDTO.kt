package com.github.biancacristina.sgobackend.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ProjectNewDTO (
    var id: Long,
    var estimate_service: Double?,
    var estimate_infra: Double?,
    var estimate_material: Double?,
    var estimate_eletronic: Double?,
    var estimate_others: Double?,

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    var estimate_startDate: LocalDateTime,

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    var estimate_endDate: LocalDateTime,

    var id_city: Long,
    var ids_labors: MutableSet<Long>
)