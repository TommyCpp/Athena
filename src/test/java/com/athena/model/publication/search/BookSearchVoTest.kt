package com.athena.model.publication.search

import com.athena.repository.jpa.BookRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener

/**
 * Created by Tommy on 2018/1/30.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml")
open class BookSearchVoTest {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun testGetSepc() {
        val bookSearchVo = BookSearchVo()
        bookSearchVo.language = "Chinese"
        bookSearchVo.titles = arrayOf("C程序设计")

        val result_1 = this.bookRepository.findAll(bookSearchVo.specification)
        Assert.assertEquals(1, result_1.count())

        bookSearchVo.language = "English"
        bookSearchVo.titles = null

        val result_2 = this.bookRepository.findAll(bookSearchVo.specification)
        Assert.assertEquals(5, result_2.count())
    }
}