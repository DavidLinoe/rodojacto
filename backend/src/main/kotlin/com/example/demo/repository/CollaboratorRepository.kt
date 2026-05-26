package com.example.demo.repository

import com.example.demo.models.entity.Collaborator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CollaboratorRepository : JpaRepository<Collaborator, Long> {

    @Query("SELECT c FROM Collaborator c JOIN FETCH c.organization WHERE c.email = ?1")
    fun findByEmail(email: String): Collaborator?

    fun findAllByOrganizationId(organizationId: Long): List<Collaborator>
}
