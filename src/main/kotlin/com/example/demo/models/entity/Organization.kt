package com.example.demo.models.entity


import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "organizations")
data class Organization(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val corporateName: String,

    @Column(nullable = false, unique = true)
    val registrationCode: String,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL])
    val collaborators: List<Collaborator> = emptyList(),

    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL])
    val devices: List<Device> = emptyList()
)
