package com.github.biancacristina.sgobackend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class City(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var name: String,

    @ManyToOne
    @JoinColumn(name="state_id")
    var state: State
) {
    @JsonIgnore
    @OneToMany(mappedBy="city")
    var clusters = mutableSetOf<Cluster>()

    @JsonIgnore
    @OneToMany(mappedBy="city")
    var projects = mutableSetOf<Project>()
}