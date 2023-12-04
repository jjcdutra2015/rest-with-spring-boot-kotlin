package com.jjcdutra.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper

object DozerMapper {

    private val mapper: Mapper = DozerBeanMapperBuilder.buildDefault()

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