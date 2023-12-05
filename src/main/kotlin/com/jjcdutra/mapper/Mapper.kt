package com.jjcdutra.mapper

import org.modelmapper.ModelMapper

object Mapper {

    private val mapper: ModelMapper = ModelMapper()

    fun <O, D> parseObject(origin: O, destination: Class<D>?): D {
        return mapper.map(origin, destination)
    }

    fun <O, D> parseObjects(origins: List<O>, destination: Class<D>?): ArrayList<D> {
        val destinations: ArrayList<D> = ArrayList()
        for (o in origins) {
            destinations.add(mapper.map(o, destination))
        }
        return destinations
    }
}