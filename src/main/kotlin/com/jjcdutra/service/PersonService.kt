package com.jjcdutra.service

import com.jjcdutra.controller.PersonController
import com.jjcdutra.data.vo.v1.PersonVO
import com.jjcdutra.exceptions.RequiredObjectIsNullException
import com.jjcdutra.exceptions.ResourceNotFoundException
import com.jjcdutra.mapper.Mapper
import com.jjcdutra.model.Person
import com.jjcdutra.repository.PersonRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(pageable: Pageable): Page<PersonVO> {
        logger.info("Finding all persons!")
        val persons = repository.findAll(pageable)

        val vos = persons.map { p -> Mapper.parseObject(p, PersonVO::class.java) }

        return vos.map { p -> p.add(linkTo(PersonController::class.java).slash(p.id).withSelfRel()) }
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

    fun create(personVO: PersonVO?): PersonVO {
        if (personVO == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${personVO.firstName}!")
        val entity = Mapper.parseObject(personVO, Person::class.java)

        val vo = Mapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(vo.id).withSelfRel()
        vo.add(withSelfRel)

        return vo
    }

    fun update(personVO: PersonVO?): PersonVO {
        if (personVO == null) throw RequiredObjectIsNullException()
        logger.info("Updating one person with ID ${personVO.id}!")
        val entity = repository.findById(personVO.id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID ${personVO.id}!")
        }

        entity.firstName = personVO.firstName
        entity.lastName = personVO.lastName
        entity.address = personVO.address
        entity.gender = personVO.gender

        val vo = Mapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(vo.id).withSelfRel()
        vo.add(withSelfRel)

        return vo
    }

    @Transactional
    fun disablePerson(id: Long): PersonVO {
        logger.info("Disabling one person with ID $id")
        repository.disablePerson(id)
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        val personVO = Mapper.parseObject(entity, PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.id).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id!")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        repository.delete(entity)
    }
}