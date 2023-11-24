package com.jjcdutra

import com.jjcdutra.exceptions.UnsupportedMathOperationException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController {
    val counter: AtomicLong = AtomicLong()

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    @RequestMapping("/sub/{numberOne}/{numberTwo}")
    fun sub(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return convertToDouble(numberOne) - convertToDouble(numberTwo)
    }

    @RequestMapping("/multi/{numberOne}/{numberTwo}")
    fun multi(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return convertToDouble(numberOne) * convertToDouble(numberTwo)
    }

    @RequestMapping("/div/{numberOne}/{numberTwo}")
    fun div(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return convertToDouble(numberOne) / convertToDouble(numberTwo)
    }

    @RequestMapping("/squareRoot/{number}")
    fun squareRoot(
        @PathVariable(value = "number") number: String?
    ): Double {
        if (!isNumeric(number))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return Math.sqrt(convertToDouble(number))
    }

    private fun convertToDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank()) return 0.0
        val number = strNumber.replace(",", ".")
        return number.toDouble()
    }

    private fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) return false
        val number = strNumber.replace(",", ".")
        return number.matches("""[-+]?[0-9]*\.?[0-9]+""".toRegex())
    }
}