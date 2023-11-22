package com.jjcdutra

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController {
    val counter: AtomicLong = AtomicLong()

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(
        @PathVariable(value = "numberOne") numberOne: String?,
        @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw Exception()
        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    private fun convertToDouble(number: String?): Double {
        return 0.0
    }

    private fun isNumeric(number: String?): Boolean {
        return false
    }
}