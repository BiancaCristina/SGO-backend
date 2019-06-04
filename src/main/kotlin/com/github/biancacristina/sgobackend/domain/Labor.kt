package com.github.biancacristina.sgobackend.domain

import javax.persistence.*

@Entity
data class Labor (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var estimate_service: Float,
    var estimate_infra: Float,
    var estimate_material: Float,
    var estimate_eletronic: Float,
    var estimate_others: Float,
    var estimate_total: Float,

    @ManyToOne
    @JoinColumn(name="cluster_id")
    var cluster: Cluster,

    @ManyToOne
    @JoinColumn(name="typeOfLabor_id")
    var typeOfLabor: TypeOfLabor

) {
    init {
        estimate_total =
                estimate_service +
                estimate_infra +
                estimate_material +
                estimate_eletronic +
                estimate_others
    }
}