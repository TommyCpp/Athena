package com.athena.model.publication.search

import com.athena.repository.jpa.BookRepository
import com.fasterxml.jackson.databind.ObjectMapper
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
    fun testGetSpecification() {
        val bookSearchVo = BookSearchVo()
        bookSearchVo.language = "Chinese"
        bookSearchVo.title = arrayOf("C程序设计")

        val result_1 = this.bookRepository.findAll(bookSearchVo.specification)
        Assert.assertEquals(1, result_1.count())

        bookSearchVo.language = "English"
        bookSearchVo.title = null

        val result_2 = this.bookRepository.findAll(bookSearchVo.specification)
        Assert.assertEquals(5, result_2.count())
    }

    @Test
    fun testJsonSerializerAndDeSerializer() {
        //test serializer
        val bookSearchVo = BookSearchVo()
        bookSearchVo.title = arrayOf("test1", "test2")
        bookSearchVo.count = 20
        bookSearchVo.page = 4
        bookSearchVo.lastCursor = 6

        System.out.println(ObjectMapper().writeValueAsString(bookSearchVo))

        //test deserializer
        val json = "{\"title\":[\"test1\",\"test2\"],\"publisherName\":null,\"language\":null,\"count\":20,\"page\":4,\"lastCursor\":6}"
        val deserializerBookSearchVo = ObjectMapper().readValue(json, BookSearchVo::class.java)
        Assert.assertTrue(deserializerBookSearchVo.title.contains("test1"))
        Assert.assertTrue(deserializerBookSearchVo.pageable.pageSize == 20)
    }

    @Test
    fun testConvertMapToBookSearchVo() {
        val map = HashMap<String, Any>()
        map.put("language", "English")
        map.put("title", arrayOf("C程序设计"))
        map.put("count", 6)

        val objectMapper = ObjectMapper()
        var bookSearchVo = objectMapper.convertValue<BookSearchVo>(map, BookSearchVo::class.java)
        Assert.assertNotNull(bookSearchVo)
        Assert.assertEquals("English", bookSearchVo.language)
        Assert.assertTrue(bookSearchVo.title.contains("C程序设计"))

    }
}