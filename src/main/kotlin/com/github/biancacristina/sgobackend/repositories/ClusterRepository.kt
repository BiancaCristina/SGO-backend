package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.Cluster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClusterRepository: JpaRepository<Cluster, Long>