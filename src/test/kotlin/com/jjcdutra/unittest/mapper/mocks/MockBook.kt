package com.jjcdutra.unittest.mapper.mocks

import com.jjcdutra.data.vo.v1.BookVO
import com.jjcdutra.model.Book
import java.util.*

class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.author = "Author Test$number"
        book.title = "Title Test$number"
        book.price = number.toDouble()
        book.id = number.toLong()
        book.launchDate = Date(number.toLong())
        return book
    }

    fun mockVO(number: Int): BookVO {
        val book = BookVO()
        book.author = "Author Test$number"
        book.title = "Title Test$number"
        book.price = number.toDouble()
        book.id = number.toLong()
        book.launchDate = Date(number.toLong())
        return book
    }
}