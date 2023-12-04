package com.jjcdutra.service

import com.jjcdutra.data.vo.v1.PersonVO
import com.jjcdutra.exceptions.ResourceNotFoundException
import com.jjcdutra.model.Person
import com.jjcdutra.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all persons!")
        return repository.findAll()
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        return entity
    }

    fun create(person: PersonVO): PersonVO {
        logger.info("Creating one person with name ${person.firstName}!")
        return repository.save(person)
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Updating one person with ID ${person.id}!")
        val entity = repository.findById(person.id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID ${person.id}!")
        }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        repository.save(entity)

        return entity
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id!")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        repository.delete(entity)
    }
}