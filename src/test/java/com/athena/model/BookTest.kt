package com.athena.model

import com.athena.repository.BookRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import java.util.ArrayList
import javax.transaction.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml")
open class BookTest {
    @Autowired
    private var repository: BookRepository? = null

    @Before
    fun setup() {
    }

    @Test
    fun testGetPublisher() {
        val book = repository!!.findOne(9787111124444L)
        Assert.assertEquals(book.getPublisher().getName(), "测试出版社")
        val authors = ArrayList<String>()
        authors.add("谭浩强")
        Assert.assertArrayEquals(authors.toTypedArray(), book.getAuthor().toTypedArray())
    }

    @Test
    fun testSaveBook() {
        val book = repository!!.findOne(9787111124444L)
        book.title = "测试书就"
        repository!!.save(book)
        Assert.assertEquals("ce,shi,shu,jiu", repository!!.findOne(9787111124444L).titlePinyin)
    }


}