package com.github.biancacristina.sgobackend.domain

import javax.persistence.*

@Entity
data class Cluster (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var name: String,

    @ManyToOne
    @JoinColumn(name="city_id")
    var city: City
)