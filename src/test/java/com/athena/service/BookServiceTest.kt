package com.athena.service

import com.athena.exception.IdOfResourceNotFoundException
import com.athena.exception.ResourceNotDeletable
import com.athena.model.Book
import com.athena.repository.jpa.BookRepository
import com.athena.repository.jpa.PublisherRepository
import com.athena.util.BookGenerator
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener

/**
 * Created by tommy on 2017/4/26.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml")
open class BookServiceTest {
    @Autowired private lateinit var service: BookService

    @Autowired private lateinit var bookRepository: BookRepository

    @Autowired private val publisherRepository: PublisherRepository? = null

    @Before
    fun setup() {

    }

    @Test
    fun testSearchByName() {
        val keywords = arrayOf("埃里克森", "程序设计")
        val pageable = PageRequest(0, 20)
        val result = service.searchBookByName(pageable, keywords)
        val expects = arrayOf(bookRepository.findOne(9783158101891L), bookRepository.findOne(9787111124444L), bookRepository.findOne(9787111125643L))
        Assert.assertEquals(3, result.totalElements) // The total result should be 3
        for (expect in expects) {
            Assert.assertTrue(result.content.contains(expect))
        }
    }

    @Test
    fun testSearchByFullName() {
        val keyword = "ut erat id"
        val pageable = PageRequest(0, 20)
        val result = service.searchBookByFullName(pageable, keyword)
        val expects = bookRepository.findOne(9785226422377L)
        Assert.assertEquals(1, result.totalElements)
        Assert.assertEquals(expects, result.content[0])
    }

    @Test
    fun testSearchByAuthors() {
        var authors = arrayOf("Aneig dlsa", "Rdlf dls")
        val pageable = PageRequest(0, 20)
        var result = service.searchBookByAuthors(pageable, authors)
        var expects = HashSet<Book>()
        expects.add(bookRepository.findOne(9783158101896L))
        expects.add(bookRepository.findOne(9783158101897L))
        Assert.assertEquals(expects, HashSet<Book>(result.content))

        authors = arrayOf("Rdlf dls", "Aneig dlsa", "Zlicn Tlidb")
        result = service.searchBookByAuthors(pageable, authors)
        expects = HashSet<Book>()
        expects.add(bookRepository.findOne(9783158101897L))
        Assert.assertEquals(expects, HashSet<Book>(result.content))

    }

    @Test
    fun testSearchByFullAuthors() {
        var authors = arrayOf("Atester", "Btester")
        var pageable = PageRequest(0, 20)
        val result = service.searchBookByFullAuthors(pageable, authors)
        val expects = ArrayList<Book>()
        expects.add(bookRepository.findOne(9783158101899L))
        Assert.assertEquals(expects, result.content)
    }

    @Test
    fun testSearchByPublisher() {
        var publisher = "不存在的出版社"
        var pageable = PageRequest(0, 20)
        var result = service.searchBookByPublisher(pageable, publisher)
        Assert.assertEquals(0, result.content.count())
        publisher = "Test Publisher"
        result = service.searchBookByPublisher(pageable, publisher)
        Assert.assertEquals("NewYork", result.content[0].publisher.location)

    }

    @Test
    fun testSaveBooks() {
        val bookGenerator: BookGenerator = BookGenerator()
        val book = bookGenerator.generateBook()
        book.publisher = publisherRepository!!.findOne("999")
        this.service.add(book)
        Assert.assertNotNull(bookRepository.findOne(book.isbn))
    }

    @Test
    fun testUpdateBooks() {
        var book_1 = this.bookRepository.findOne(9783158101891L)
        var book_2 = this.bookRepository.findOne(9783158101895L)
        var book_list = arrayListOf<Book>(book_1, book_2)
        book_1.title = "修改过的Title"
        book_2.title = "修改过的Title2"
        this.service.update(book_1)
        Assert.assertEquals("修改过的Title", this.bookRepository.findOne(9783158101891L).title)
        this.service.update(book_list)
        Assert.assertEquals("修改过的Title2", this.bookRepository.findOne(9783158101895L).title)

        //Test Exception
        val book_3 = this.bookRepository.findOne(9783158101891L)
        book_3.isbn = 8888888888888L
        var flag = false
        try {
            this.service.update(book_3)
        } catch (e: IdOfResourceNotFoundException) {
            flag = true
        }
        Assert.assertTrue(flag)
    }

    @Test(expected = ResourceNotDeletable::class)
    fun testDeleteBookWithNotDeletableBookCopy() {
        val book: Book = this.bookRepository.findOne(9783158101901L)
        this.service.delete(book)
    }


}
