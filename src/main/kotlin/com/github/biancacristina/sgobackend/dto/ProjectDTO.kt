package com.github.biancacristina.sgobackend.dto

import java.time.LocalDateTime

data class ProjectDTO (
    var id: Long,
    var estimate_startDate: LocalDateTime,
    var estimate_endDate: LocalDateTime,
    var status: String
)