package com.github.biancacristina.sgobackend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class CostAggregation (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var name: String,

    @ManyToOne
    @JoinColumn(name="typeOfCostAggregation_id")
    var typeOfCostAggregation: TypeOfCostAggregation
) {
    @JsonIgnore
    @OneToMany(mappedBy= "costAggregation")
    var labors = mutableSetOf<Labor>()
}