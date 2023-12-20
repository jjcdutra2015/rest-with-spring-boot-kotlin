package com.jjcdutra.mockito.service

import com.jjcdutra.exceptions.RequiredObjectIsNullException
import com.jjcdutra.repository.BookRepository
import com.jjcdutra.service.BookService
import com.jjcdutra.unittest.mapper.mocks.MockBook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUp() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()

        assertNotNull(books)
        assertEquals(14, books.size)

        val bookOne = books[1]
        assertNotNull(bookOne)
        assertNotNull(bookOne.id)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", bookOne.author)
        assertEquals("Title Test1", bookOne.title)
        assertEquals(1.0, bookOne.price)
        assertEquals(Date(1), bookOne.launchDate)

        val bookFour = books[4]
        assertNotNull(bookFour)
        assertNotNull(bookFour.id)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("</api/book/v1/4>;rel=\"self\""))
        assertEquals("Author Test4", bookFour.author)
        assertEquals("Title Test4", bookFour.title)
        assertEquals(4.0, bookFour.price)
        assertEquals(Date(4), bookFour.launchDate)

        val bookSeven = books[7]
        assertNotNull(bookSeven)
        assertNotNull(bookSeven.id)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("</api/book/v1/7>;rel=\"self\""))
        assertEquals("Author Test7", bookSeven.author)
        assertEquals("Title Test7", bookSeven.title)
        assertEquals(7.0, bookSeven.price)
        assertEquals(Date(7), bookSeven.launchDate)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)

        `when`(repository.findById(book.id)).thenReturn(Optional.of(book))

        val result = service.findById(book.id)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(1.0, result.price)
        assertEquals(Date(1), result.launchDate)
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(1.0, result.price)
        assertEquals(Date(1), result.launchDate)
    }

    @Test
    fun createWithNullObject() {
        val exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.create(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(1.0, result.price)
        assertEquals(Date(1), result.launchDate)
    }

    @Test
    fun updateWithNullObject() {
        val exception = assertThrows(RequiredObjectIsNullException::class.java) {
            service.update(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)
        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}