package com.jjcdutra

import com.jjcdutra.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {

    private val counter: AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): Person {
        logger.info("Finding one person!")

        return Person(
            id = counter.incrementAndGet(),
            firstName = "Julio",
            lastName = "Dutra",
            address = "Carmo - Rio de Janeiro - Brasil",
            gender = "Male"
        )
    }
}