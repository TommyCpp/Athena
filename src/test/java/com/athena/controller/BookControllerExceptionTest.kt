package com.athena.controller

import com.athena.model.Book
import com.athena.model.Publisher
import com.athena.repository.jpa.BookRepository
import com.athena.util.BookGenerator
import com.athena.util.IdentityGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by Tommy on 2017/8/21.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml")
@WebAppConfiguration
@ActiveProfiles("fakeMongo")
open class BookControllerExceptionTest {
    @Autowired private val context: WebApplicationContext? = null
    private val mockBookGenerator: BookGenerator = BookGenerator()
    @Autowired private val bookResository: BookRepository? = null

    private var mvc: MockMvc? = null
    @Value("\${web.url.prefix}") private var url_prefix: String = ""
    private val identity: IdentityGenerator = IdentityGenerator()


    @Before fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()

    }

    @Test fun testMongoConnectionException() {
        var books: MutableList<Book> = arrayListOf()
        var publisher = Publisher()
        publisher.id = "999"
        for (i in 1..4) {
            val book = this.mockBookGenerator.generateBook()
            book.publisher = publisher
            books.add(book)
        }
        mvc!!.perform(post(this.url_prefix + "/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(books))
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().`is`(500))

        Assert.assertNull(this.bookResository!!.findOne(books[0].isbn))

    }


}