package com.github.biancacristina.sgobackend.repositories

import com.github.biancacristina.sgobackend.domain.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<Project, Long>