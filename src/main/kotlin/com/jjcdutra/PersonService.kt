package com.jjcdutra

import com.jjcdutra.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {

    private val counter: AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Finding all persons!")

        val persons = mutableListOf<Person>()

        for (i in 0..7) {
            val person = mockPerson(i)
            persons.add(person)
        }

        return persons
    }

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

    fun create(person: Person) = person

    fun update(person: Person) = person

    fun delete(id: Long) {}

    private fun mockPerson(i: Int): Person {
        return Person(
            id = counter.incrementAndGet(),
            firstName = "First name $i",
            lastName = "Last name $i",
            address = "Some address in Brazil",
            gender = "Male"
        )
    }
}