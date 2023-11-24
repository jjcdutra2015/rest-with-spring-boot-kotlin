package com.jjcdutra.model

data class Person(
    var id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val gender: String = ""
)
