package com.jjcdutra.converter

object NumberConverter {

    fun convertToDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank()) return 0.0
        val number = strNumber.replace(",", ".")
        return number.toDouble()
    }

    fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) return false
        val number = strNumber.replace(",", ".")
        return number.matches("""[-+]?[0-9]*\.?[0-9]+""".toRegex())
    }
}