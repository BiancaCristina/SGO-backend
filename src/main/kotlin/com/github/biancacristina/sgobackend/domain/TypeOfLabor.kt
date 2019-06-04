package com.github.biancacristina.sgobackend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class TypeOfLabor (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var typeOfLabor: String
) {

    @JsonIgnore
    @OneToMany(mappedBy= "typeOfLabor")
    var labors = mutableSetOf<Labor>()
}