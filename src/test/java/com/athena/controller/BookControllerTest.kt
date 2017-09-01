package com.athena.controller

import com.athena.model.*
import com.athena.repository.jpa.BookRepository
import com.athena.security.model.Account
import com.athena.security.model.JwtAuthenticationToken
import com.athena.util.BookGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.lordofthejars.nosqlunit.annotation.UsingDataSet
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml")
@WebAppConfiguration
open class BookControllerTest {
    @Autowired private val context: WebApplicationContext? = null
    @Autowired private val applicationContext: ApplicationContext? = null
    @Autowired private val bookRepository: BookRepository? = null

    private var mvc: MockMvc? = null
    private val mockBookGenerator: BookGenerator = BookGenerator()
    @Value("\${web.url.prefix}") private var url_prefix: String = ""

    @Before fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(springSecurity()).build()
    }

    private fun createAuthentication(role: String): JwtAuthenticationToken {
        val encoder = BCryptPasswordEncoder()
        val user = User()
        user.identity = role
        user.username = "reader"
        user.password = encoder.encode("123456")
        user.id = 1L
        user.email = "test@test.com"
        user.wechatId = "testWechat"
        user.phoneNumber = "11111111111"
        val principal = Account(user)

        return JwtAuthenticationToken(principal, true)
    }

    private fun authentication(role: String = "ROLE_READER"): RequestPostProcessor {
        val securityContext = SecurityContextHolder.createEmptyContext()
        securityContext.authentication = this.createAuthentication(role)

        return securityContext(securityContext)
    }

    @Test
    fun testBookSearchByPartialTitle() {

        mvc!!.perform(get(this.url_prefix + "/books?title=elit").with(this.authentication()))
                .andDo(print())
                .andExpect(content().json("{\"content\":[{\"isbn\":9784099507505,\"publishDate\":1474128000000,\"categoryId\":\"TC331A\",\"version\":4,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"adipiscing elit\",\"titlePinyin\":null,\"titleShortPinyin\":null,\"subtitle\":null,\"language\":\"English\",\"price\":520.5,\"publisher\":{\"id\":\"922\",\"name\":\"Test Publisher\",\"location\":\"NewYork\"},\"translator\":[],\"author\":[\"Steffen Catcherside\"]}],\"totalElements\":1,\"totalPages\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":null,\"numberOfElements\":1,\"first\":true}"))

    }

    @Test
    fun testBookSearchByFullTitle() {
        mvc!!.perform(get(this.url_prefix + "/books?title=consequat in consequat").with(this.authentication()))
                .andDo(print())
                .andExpect(content().json("{\"content\":[{\"isbn\":9785867649253,\"publishDate\":1468684800000,\"categoryId\":\"TC331C\",\"version\":5,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"consequat in consequat\",\"titlePinyin\":null,\"titleShortPinyin\":null,\"subtitle\":null,\"language\":\"English\",\"price\":85.25,\"publisher\":{\"id\":\"817\",\"name\":\"TestDn Publisher\",\"location\":\"NewYork\"},\"author\":[\"Lian Hubback\"],\"translator\":[]}],\"totalElements\":1,\"last\":true,\"totalPages\":1,\"size\":20,\"number\":0,\"sort\":null,\"first\":true,\"numberOfElements\":1}"))
                .andExpect(header().string("X-Total-Count", "1")).andExpect(header().string("Links", "<http://localhost/api/v1/books?page=0&title=consequat in consequat>; rel=\"last\",<http://localhost/api/v1/books?page=0&title=consequat in consequat>; rel=\"first\""))
        mvc!!.perform(get(this.url_prefix + "/books?title=consequat&match_all=true").with(this.authentication()))
                .andExpect(header().string("X-Total-Count", "0"))
    }


    @Test
    fun testBookSearchByAuthor() {
        mvc!!.perform(get(this.url_prefix + "/books?author=Lian Hubback")
                .with(this.authentication()))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().json("{\"content\":[{\"isbn\":9785867649253,\"publishDate\":1468684800000,\"categoryId\":\"TC331C\",\"version\":5,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"consequat in consequat\",\"titlePinyin\":null,\"titleShortPinyin\":null,\"subtitle\":null,\"language\":\"English\",\"price\":85.25,\"publisher\":{\"id\":\"817\",\"name\":\"TestDn Publisher\",\"location\":\"NewYork\"},\"author\":[\"Lian Hubback\"],\"translator\":[]}],\"totalElements\":1,\"totalPages\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":null,\"first\":true,\"numberOfElements\":1}"))
    }

    @Test
    fun testBookSearchByAuthors() {
        mvc!!.perform(get(this.url_prefix + "/books?author=Aneig dlsa,Rdlf dls")
                .with(this.authentication()))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().json("{\"content\":[{\"isbn\":9783158101896,\"publishDate\":1478966400000,\"categoryId\":\"TC331C\",\"version\":1,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"测试作者书籍\",\"titlePinyin\":\"ce,shi,zuo,zhe,shu,ji\",\"titleShortPinyin\":\"cszzsj\",\"subtitle\":null,\"language\":\"Chinese\",\"price\":57.22,\"publisher\":{\"id\":\"127\",\"name\":\"TestDll Publisher\",\"location\":\"NewYork\"},\"translator\":[],\"author\":[\"Aneig dlsa\",\"Bianfd sld\",\"Rdlf dls\"]},{\"isbn\":9783158101897,\"publishDate\":1478966400000,\"categoryId\":\"TC331C\",\"version\":1,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"测试多个作者书籍\",\"titlePinyin\":\"ce,shi,duo,ge,zuo,zhe,shu,ji\",\"titleShortPinyin\":\"csdgzzsj\",\"subtitle\":null,\"language\":\"Chinese\",\"price\":57.22,\"publisher\":{\"id\":\"127\",\"name\":\"TestDll Publisher\",\"location\":\"NewYork\"},\"translator\":[],\"author\":[\"Aneig dlsa\",\"Rdlf dls\",\"Zlicn Tlidb\"]}],\"totalElements\":2,\"last\":true,\"totalPages\":1,\"number\":0,\"size\":20,\"sort\":null,\"first\":true,\"numberOfElements\":2}"))
    }

    @Test
    fun testRateLimit() {
        val processor: RequestPostProcessor = RequestPostProcessor { request ->
            request.remoteAddr = "192.168.1.1"
            request
        }
        for (i in 1..5) {
            // Note that the search.limit.get.times in config.properties must be 3
            if (i < 4) {
                mvc!!.perform(get(this.url_prefix + "/books?author=Aneig dlsa,Rdlf dls").with(processor)).andExpect(status().isOk)
            } else {
                mvc!!.perform(get(this.url_prefix + "/books?author=Aneig dlsa,Rdlf dls").with(processor)).andExpect(status().`is`(429))
            }
        }
    }

    @Test
    @UsingDataSet(locations = arrayOf("/batch.json"), loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    fun testCreateBook() {
        /**
         * Setup
         *
         * */
        var books: ArrayList<Book> = arrayListOf()
        var publisher = Publisher()
        publisher.id = "999"
        for (i in 1..4) {
            val book = this.mockBookGenerator.generateBook()
            book.publisher = publisher
            books.add(book)
        }

        /**
         * Test privilege
         *
         * only admin should be allowed to create book
         */
        mvc!!.perform(post(this.url_prefix + "/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(books))
                .with(this.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isCreated)


        mvc!!.perform(post(this.url_prefix + "/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(books))
                .with(this.authentication("ROLE_READER"))
        )
                .andExpect(status().isForbidden)


        /**
         * Test whether the books has been saved
         *
         */
        for (book in books) {
            Assert.assertNotNull(this.bookRepository!!.findOne(book.isbn))
        }


        /**
         * Test Exception
         *
         * publisher doesn't exist in db
         * */
        books = arrayListOf()
        var errorBook = this.mockBookGenerator.generateBook()
        publisher = Publisher()
        publisher.id = "test" //publisher that doesn't exist
        errorBook.publisher = publisher
        books.add(errorBook)
        mvc!!.perform(post(this.url_prefix + "/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(books))
                .with(this.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isBadRequest)


        /**
         * Test Transaction
         *
         * 1. When exception happens in insert books (Exception in MySQL)
         * 2. When exception happens in insert batch (Exception in MongoDB)
         */
        books = arrayListOf()
        books.add(errorBook)
        var book = this.mockBookGenerator.generateBook()
        publisher = Publisher()
        publisher.id = "922"
        book.publisher = publisher
        books.add(book)

        var result = mvc!!.perform(post(this.url_prefix + "/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(books))
                .with(this.authentication("ROLE_ADMIN"))
        )
                .andReturn()

        if (result.response.status != 201) {
            //Exception happens
            Assert.assertNull(this.bookRepository!!.findOne(book.isbn))
        }


    }


    @Test
    fun testCreateCopy() {
        var copyInfo: CopyInfo = CopyInfo()
        copyInfo.status = CopyStatus.BOOKED
        var copyInfoList: List<CopyInfo> = arrayListOf(copyInfo)


        this.mvc!!.perform(post(this.url_prefix + "/books/9785226422377/copy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(copyInfoList))
                .with(this.authentication("ROLE_ADMIN"))
        )
                .andDo(print())
                .andExpect(status().isCreated)




    }
}

