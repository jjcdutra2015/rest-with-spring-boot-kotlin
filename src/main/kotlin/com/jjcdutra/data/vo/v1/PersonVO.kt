package com.jjcdutra.data.vo.v1

import org.springframework.hateoas.RepresentationModel

data class PersonVO(

    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
    var enabled: Boolean = true
) : RepresentationModel<PersonVO>()
