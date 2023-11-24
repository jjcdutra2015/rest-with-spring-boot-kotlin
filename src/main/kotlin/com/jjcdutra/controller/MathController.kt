package com.jjcdutra.controller

import com.jjcdutra.converter.NumberConverter
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
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return NumberConverter.convertToDouble(numberOne) + NumberConverter.convertToDouble(numberTwo)
    }

    @RequestMapping("/sub/{numberOne}/{numberTwo}")
    fun sub(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return NumberConverter.convertToDouble(numberOne) - NumberConverter.convertToDouble(numberTwo)
    }

    @RequestMapping("/multi/{numberOne}/{numberTwo}")
    fun multi(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return NumberConverter.convertToDouble(numberOne) * NumberConverter.convertToDouble(numberTwo)
    }

    @RequestMapping("/div/{numberOne}/{numberTwo}")
    fun div(
        @PathVariable(value = "numberOne") numberOne: String?, @PathVariable(value = "numberTwo") numberTwo: String?
    ): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return NumberConverter.convertToDouble(numberOne) / NumberConverter.convertToDouble(numberTwo)
    }

    @RequestMapping("/squareRoot/{number}")
    fun squareRoot(
        @PathVariable(value = "number") number: String?
    ): Double {
        if (!NumberConverter.isNumeric(number))
            throw UnsupportedMathOperationException("Please, set a numeric value!")
        return Math.sqrt(NumberConverter.convertToDouble(number))
    }
}