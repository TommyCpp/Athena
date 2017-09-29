package com.athena.model

import com.athena.repository.jpa.BookRepository
import com.athena.repository.jpa.PublisherRepository
import com.athena.util.BookGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import java.util.*
import javax.transaction.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml")
open class BookTest {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var publisherRepository: PublisherRepository

    private var generator: BookGenerator = BookGenerator()

    @Test
    fun testGetPublisher() {
        val book = bookRepository.findOne(9787111124444L)
        Assert.assertEquals(book.publisher.name, "测试出版社")
        val authors = ArrayList<String>()
        authors.add("谭浩强")
        Assert.assertArrayEquals(authors.toTypedArray(), book.author.toTypedArray())
    }

    @Test
    fun testSaveBook() {
        val book = this.generator.generateBook()
        book.publisher = this.publisherRepository.findOne("127")
        book.title = "测试书就"
        bookRepository.save(book)
        Assert.assertEquals("ce,shi,shu,jiu", bookRepository.findOne(book.isbn).titlePinyin)

        var another_book = this.bookRepository.findOne(9787111124444L)
        another_book.title = "测试书就"
        another_book = bookRepository.save(another_book)
        Assert.assertNotNull("ce,shi,shu,jiu", another_book.titlePinyin)
        Assert.assertEquals("测试书就", bookRepository.findOne(9787111124444L).title)
        Assert.assertEquals("ce,shi,shu,jiu", bookRepository.findOne(9787111124444L).titlePinyin)
        //todo: to handle the operation before em.merge


    }

    @Test
    fun testGetByPinyin() {
        val books = bookRepository.getBooksByTitlePinyin("cchenxvsheji")
        Assert.assertEquals(9787111124444L, books[0].isbn)
    }

    @Test
    fun testGetByAuthor() {
        var author = ArrayList<String>()
        author.add("Nickola Dolling")
        val pageable = PageRequest(0, 20)


        var books = bookRepository.getBookBy_authorContains(pageable, "Nickola Dolling").content
        Assert.assertEquals(9783158101890L, books[0].isbn)

    }


    @Test
    fun testChineseTitle() {
        val book = bookRepository.findOne(9783158101891L)
        System.out.println(ObjectMapper().writeValueAsString(book))
    }

    @Test
    fun testGetCopy() {
        val book = bookRepository.findOne(9787111125643L)
        Assert.assertNotEquals(0, book.copies.size)
    }


}