package com.example.demo.models.entity

import com.example.demo.models.enums.AccessLevel
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "collaborators")
data class Collaborator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val fullName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val accessLevel: AccessLevel,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    val organization: Organization
) : java.security.Principal {
    override fun getName(): String = email
    override fun toString(): String = "Collaborator(id=$id, email=$email, accessLevel=$accessLevel)"
}