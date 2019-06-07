package com.github.biancacristina.sgobackend.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.biancacristina.sgobackend.domain.enums.Status
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Project (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name="city_id")
    var city: City,

    @Enumerated
    var status: Status
) {
    @OneToMany(mappedBy= "project")
    var labors = mutableSetOf<Labor>()

    var estimate_total: Double? = 0.0

    init {
        // Indicates that if the value is null, so the value is 0
        estimate_service = estimate_service?:0.0
        estimate_infra = estimate_infra?:0.0
        estimate_material = estimate_material?:0.0
        estimate_eletronic = estimate_eletronic?:0.0
        estimate_others = estimate_others?:0.0

        // Calculate the estimate total of the labor
        estimate_total = estimate_total?.plus(estimate_service?:0.0)
        estimate_total = estimate_total?.plus(estimate_infra?:0.0)
        estimate_total = estimate_total?.plus(estimate_material?:0.0)
        estimate_total = estimate_total?.plus(estimate_eletronic?:0.0)
        estimate_total = estimate_total?.plus(estimate_others?:0.0)
    }
}