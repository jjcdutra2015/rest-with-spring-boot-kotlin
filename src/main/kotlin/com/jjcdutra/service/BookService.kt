package com.jjcdutra.service

import com.jjcdutra.controller.BookController
import com.jjcdutra.data.vo.v1.BookVO
import com.jjcdutra.exceptions.RequiredObjectIsNullException
import com.jjcdutra.exceptions.ResourceNotFoundException
import com.jjcdutra.mapper.Mapper
import com.jjcdutra.model.Book
import com.jjcdutra.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<BookVO>

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(pageable: Pageable): PagedModel<EntityModel<BookVO>> {
        logger.info("Finding all books!")
        val books = repository.findAll(pageable)

        val vos = books.map { b -> Mapper.parseObject(b, BookVO::class.java) }

        return assembler.toModel(vos.map { b -> b.add(linkTo(BookController::class.java).slash(b.id).withSelfRel()) })
    }

    fun findById(id: Long): BookVO {
        logger.info("Finding one book with ID $id")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        val bookVO = Mapper.parseObject(entity, BookVO::class.java)

        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.id).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun create(bookVO: BookVO?): BookVO {
        if (bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Creating one book with title ${bookVO.author}!")
        val entity = Mapper.parseObject(bookVO, Book::class.java)

        val vo = Mapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(vo.id).withSelfRel()
        vo.add(withSelfRel)

        return vo
    }

    fun update(bookVO: BookVO?): BookVO {
        if (bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Updating one person with ID ${bookVO.id}!")
        val entity = repository.findById(bookVO.id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID ${bookVO.id}!")
        }

        entity.author = bookVO.author
        entity.launchDate = bookVO.launchDate
        entity.price = bookVO.price
        entity.title = bookVO.title

        val vo = Mapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(vo.id).withSelfRel()
        vo.add(withSelfRel)

        return vo
    }

    fun delete(id: Long) {
        logger.info("Deleting one book with ID $id!")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        repository.delete(entity)
    }
}