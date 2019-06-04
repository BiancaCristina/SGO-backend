package com.github.biancacristina.sgobackend.domain

import javax.persistence.*

@Entity
data class Labor (
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long,

    var estimate_service: Double,
    var estimate_infra: Double,
    var estimate_material: Double,
    var estimate_eletronic: Double,
    var estimate_others: Double,

    @ManyToOne
    @JoinColumn(name="cluster_id")
    var cluster: Cluster,

    @ManyToOne
    @JoinColumn(name="typeOfLabor_id")
    var typeOfLabor: TypeOfLabor

) {
    var estimate_total: Double = estimate_service +
                                    estimate_infra +
                                    estimate_material +
                                    estimate_eletronic +
                                    estimate_others
}