package com.github.biancacristina.sgobackend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class TypeOfCostAggregation (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var name: String
) {
    @JsonIgnore
    @OneToMany(mappedBy="typeOfCostAggregation")
    var costAggregations = mutableSetOf<CostAggregation>()
}