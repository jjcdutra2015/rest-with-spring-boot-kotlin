package com.jjcdutra.integrationtests.wrappers

import com.fasterxml.jackson.annotation.JsonProperty

class PersonWrapper {

    @JsonProperty("_embedded")
    val embedded: PersonEmbeddedVO? = null
}