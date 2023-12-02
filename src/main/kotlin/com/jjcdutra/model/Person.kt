package com.jjcdutra.model

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "first_name", nullable = false, length = 80)
    val firstName: String = "",

    @Column(name = "last_name", nullable = false, length = 80)
    val lastName: String = "",

    @Column(nullable = false, length = 100)
    val address: String = "",

    @Column(nullable = false, length = 6)
    val gender: String = ""
)
