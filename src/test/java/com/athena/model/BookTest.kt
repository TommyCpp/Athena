package com.athena.model

import com.athena.model.publication.Book
import com.athena.repository.jpa.BookRepository
import com.athena.repository.jpa.PublisherRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import util.BookGenerator
import java.util.*
import javax.persistence.criteria.Expression
import javax.transaction.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:book_copy.xml")
open class BookTest {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var publisherRepository: PublisherRepository

    private var generator: BookGenerator = BookGenerator()

    @Test
    @Transactional
    open fun testGetPublisher() {
        val book = bookRepository.findOne(9787111124444L)
        Assert.assertEquals(book.publisher.name, "测试出版社")
        val authors = ArrayList<String>()
        authors.add("谭浩强")
        Assert.assertArrayEquals(authors.toTypedArray(), book.author.toTypedArray())
    }

    @Test
    fun testSaveBook() {
        var book = this.generator.generateBook()
        book.publisher = this.publisherRepository.findOne("127")
        book.title = "测试书就"
        bookRepository.save(book)
        book = bookRepository.findOne(book.isbn)
        if (book.language == "Chinese") {
            Assert.assertEquals("ce,shi,shu,jiu", book.titlePinyin)
        }

        var another_book = this.bookRepository.findOne(9787111124444L)
        another_book.title = "测试书就"
        bookRepository.save(another_book)
        Assert.assertNotNull("ce,shi,shu,jiu", another_book.titlePinyin)
        bookRepository.flush() // crucial
        another_book = bookRepository.findOne(9787111124444L)
        Assert.assertEquals("测试书就", another_book.title)
        Assert.assertEquals("ce,shi,shu,jiu", another_book.titlePinyin)

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


        var books = bookRepository.getBookByAuthor(pageable, "Nickola Dolling").content
        Assert.assertEquals(9783158101890L, books[0].isbn)

    }


    @Test
    @Transactional
    open fun testChineseTitle() {
        val book = bookRepository.findOne(9783158101891L)
        System.out.println(ObjectMapper().writeValueAsString(book))
    }

    @Test
    @Transactional
    open fun testGetCopy() {
        val book = bookRepository.findOne(9787111125643L)
        Assert.assertNotEquals(0, book.copies.size)
    }

    @Test
    fun testListener() {
        var book = bookRepository.findOne(9785226422377L)
        book.language = "Chinese"
        book.title = "测试书籍"
        book = bookRepository.saveAndFlush(book)
        Assert.assertEquals("ce,shi,shu,ji", book.titlePinyin)
    }

    /**
     * Test Specification.
     *
     * Assert 1. Given 2 titles, find all books whose title within given title. Should be 3 books.
     * Assert 2. Based on Assert 1's specification. Add a specification that requires book's authors must has Tdicko. Should only left 1 book.
     * */
    @Test
    fun testGetBook_Given2titlesAnd1Author_ShouldFindSuitableBooks() {
        //Assert 1
        var specification = Specification<Book>() { root, criteriaQuery, criteriaBuilder ->
            root.get<String>("title").`in`(arrayListOf("第二部测试书", "第三部测试书"))
        }

        var books: MutableList<Any?> = bookRepository.findAll(specification)

        Assert.assertEquals(3, books.count())

        //Assert 2
        specification = Specification() { root, criteriaQuery, criteriaBuilder ->
            val authors: Expression<Collection<String>> = root.get("author")
            criteriaBuilder
                    .and(
                            root.get<String>("title").`in`(arrayListOf("第二部测试书", "第三部测试书")),
                            criteriaBuilder.isMember("Tdicko", authors)
                    )
        }

        books = bookRepository.findAll(specification)

        Assert.assertEquals(1,books.count())
    }


}