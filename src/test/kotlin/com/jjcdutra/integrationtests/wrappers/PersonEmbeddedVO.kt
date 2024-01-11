package com.jjcdutra.integrationtests.wrappers

import com.fasterxml.jackson.annotation.JsonProperty
import com.jjcdutra.integrationtests.vo.PersonVO

class PersonEmbeddedVO {

    @JsonProperty("personVOList")
    val persons: List<PersonVO>? = null
}