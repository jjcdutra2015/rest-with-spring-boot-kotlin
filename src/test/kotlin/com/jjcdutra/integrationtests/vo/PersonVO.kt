package com.jjcdutra.integrationtests.vo

data class PersonVO(

    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
    var enabled: Boolean = true
)
