package com.github.biancacristina.sgobackend.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.github.biancacristina.sgobackend.domain.enums.Status
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class CostAggregation (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    var estimate_startDate: LocalDateTime,

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    var estimate_endDate: LocalDateTime,

    @ManyToOne
    @JoinColumn(name="typeOfCostAggregation_id")
    var typeOfCostAggregation: TypeOfCostAggregation,

    @Enumerated
    var status: Status
)