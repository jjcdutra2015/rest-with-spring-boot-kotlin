package com.jjcdutra.data.vo.v1

import org.springframework.hateoas.RepresentationModel
import java.util.*

data class BookVO(

    var id: Long = 0,
    var author: String = "",
    var launchDate: Date? = null,
    var price: Double = 0.0,
    var title: String = ""
) : RepresentationModel<BookVO>()
