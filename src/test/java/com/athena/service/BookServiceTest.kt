package com.athena.service

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
import javax.transaction.Transactional

/**
 * Created by tommy on 2017/4/26.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml")
open class BookServiceTest {
    @Autowired private val service: BookService? = null

    @Autowired private val bookRepository: BookRepository? = null

    @Autowired private val publisherRepository: PublisherRepository? = null

    @Before fun setup() {

    }

    @Test fun testSearchByName() {
        val keywords = arrayOf("埃里克森", "程序设计")
        val pageable = PageRequest(0, 20)
        val result = service!!.searchBookByName(pageable, keywords)
        val expects = arrayOf(bookRepository!!.findOne(9783158101891L), bookRepository.findOne(9787111124444L), bookRepository.findOne(9787111125643L))
        Assert.assertEquals(3, result.totalElements) // The total result should be 3
        for (expect in expects) {
            Assert.assertTrue(result.content.contains(expect))
        }
    }

    @Test fun testSearchByFullName() {
        val keyword = "C程序设计"
        val pageable = PageRequest(0, 20)
        val result = service!!.searchBookByFullName(pageable, keyword)
        val expects = bookRepository!!.findOne(9787111124444L)
        Assert.assertEquals(1, result.totalElements)
        Assert.assertEquals(expects, result.content[0])
    }

    @Test fun testSearchByAuthors() {
        var authors = arrayOf("Dneig dlsa", "Rdlf dls")
        val pageable = PageRequest(0, 20)
        var result = service!!.searchBookByAuthors(pageable, authors)
        var expects = HashSet<Book>()
        expects.add(bookRepository!!.findOne(9783158101896L))
        expects.add(bookRepository.findOne(9783158101897L))
        Assert.assertEquals(expects, HashSet<Book>(result.content))

        authors = arrayOf("Rdlf dls", "Dneig dlsa", "Dlicn Tlidb")
        result = service.searchBookByAuthors(pageable, authors)
        expects = HashSet<Book>()
        expects.add(bookRepository.findOne(9783158101897L))
        Assert.assertEquals(expects, HashSet<Book>(result.content))

    }

    @Test fun testSearchByFullAuthors() {
        var authors = arrayOf("Atester", "Btester")
        var pageable = PageRequest(0, 20)
        val result = service!!.searchBookByFullAuthors(pageable, authors)
        val expects = ArrayList<Book>()
        expects.add(bookRepository!!.findOne(9783158101899L))
        Assert.assertEquals(expects, result.content)
    }

    @Test fun testSearchByPublisher() {
        var publisher = "不存在的出版社"
        var pageable = PageRequest(0, 20)
        var result = service!!.searchBookByPublisher(pageable, publisher)
        Assert.assertEquals(0, result.content.count())
        publisher = "Test Publisher"
        result = service!!.searchBookByPublisher(pageable, publisher)
        Assert.assertEquals("NewYork", result.content[0].publisher.location)

    }

    @Test fun testSaveBooks() {
        val bookGenerator: BookGenerator = BookGenerator()
        val book = bookGenerator.generateBook()
        book.publisher = publisherRepository!!.findOne("999")
        this.service!!.saveBook(book)
        Assert.assertNotNull(bookRepository!!.findOne(book.isbn))
    }

}
