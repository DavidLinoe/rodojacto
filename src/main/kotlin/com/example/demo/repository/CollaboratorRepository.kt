package com.example.demo.repository

import com.example.demo.models.entity.Collaborator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CollaboratorRepository : JpaRepository<Collaborator, Long> {
    fun findByEmail(email: String): Collaborator?
    fun findAllByOrganizationId(organizationId: Long): List<Collaborator>
}