package com.github.biancacristina.sgobackend.domain

import javax.persistence.*

@Entity
data class City(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var name: String,

    @ManyToOne
    @JoinColumn(name="state_id")
    var state: State
)