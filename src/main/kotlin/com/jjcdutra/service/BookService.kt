package com.jjcdutra.service

import com.jjcdutra.controller.PersonController
import com.jjcdutra.data.vo.v1.BookVO
import com.jjcdutra.exceptions.RequiredObjectIsNullException
import com.jjcdutra.exceptions.ResourceNotFoundException
import com.jjcdutra.mapper.Mapper
import com.jjcdutra.model.Book
import com.jjcdutra.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info("Finding all books!")
        val books = repository.findAll()

        val listBook = Mapper.parseObjects(books, BookVO::class.java)

        for (book in listBook) {
            val withSelfRel = linkTo(PersonController::class.java).slash(book.id).withSelfRel()
            book.add(withSelfRel)
        }

        return listBook
    }

    fun findById(id: Long): BookVO {
        logger.info("Finding one book with ID $id")
        val entity = repository.findById(id).orElseThrow {
            ResourceNotFoundException("Not records found for this ID $id!")
        }
        val bookVO = Mapper.parseObject(entity, BookVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(bookVO.id).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun create(bookVO: BookVO?): BookVO {
        if (bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Creating one book with title ${bookVO.author}!")
        val entity = Mapper.parseObject(bookVO, Book::class.java)

        val vo = Mapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(vo.id).withSelfRel()
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
        val withSelfRel = linkTo(PersonController::class.java).slash(vo.id).withSelfRel()
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