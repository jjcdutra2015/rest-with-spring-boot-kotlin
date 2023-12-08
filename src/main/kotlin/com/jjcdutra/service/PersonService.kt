package com.jjcdutra.service

import com.jjcdutra.controller.PersonController
import com.jjcdutra.data.vo.v1.PersonVO
import com.jjcdutra.exceptions.ResourceNotFoundException
import com.jjcdutra.mapper.Mapper
import com.jjcdutra.model.Person
import com.jjcdutra.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all persons!")
        val persons = repository.findAll()

        return Mapper.parseObjects(persons, PersonVO::class.java)
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        val personVO = Mapper.parseObject(entity, PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.id).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating one person with name ${personVO.firstName}!")
        val entity = Mapper.parseObject(personVO, Person::class.java)
        return Mapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun update(personVO: PersonVO): PersonVO {
        logger.info("Updating one person with ID ${personVO.id}!")
        val entity = repository.findById(personVO.id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID ${personVO.id}!")
        }

        entity.firstName = personVO.firstName
        entity.lastName = personVO.lastName
        entity.address = personVO.address
        entity.gender = personVO.gender

        return Mapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id!")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        repository.delete(entity)
    }
}