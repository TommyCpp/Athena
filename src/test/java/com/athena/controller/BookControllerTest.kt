package com.athena.controller;

import com.athena.model.User
import com.athena.security.model.Account
import com.athena.security.model.JwtAuthenticationToken
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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

    private var mvc: MockMvc? = null

    @Before fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(springSecurity()).build()

    }

    private fun createAuthentication(): JwtAuthenticationToken {
        val encoder = BCryptPasswordEncoder()
        val user = User()
        user.identity = "READER"
        user.username = "reader"
        user.password = encoder.encode("123456")
        user.id = 1L
        user.email = "test@test.com"
        user.wechatId = "testWechat"
        user.phoneNumber = "11111111111"
        val principal = Account(user)

        return JwtAuthenticationToken(principal, true)
    }

    private fun authentication(): RequestPostProcessor {
        val securityContext = SecurityContextHolder.createEmptyContext()
        securityContext.authentication = this.createAuthentication()

        return securityContext(securityContext)
    }

    @Test
    fun testBookSearchByPartialTitle() {

        mvc!!.perform(get("/api/v1/books?title=elit").with(this.authentication()))
                .andDo(print())
                .andExpect(content().json("{\"content\":[{\"isbn\":9784099507505,\"publishDate\":\"2016-09-18\",\"categoryId\":\"TC331A\",\"version\":4,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"adipiscing elit\",\"titlePinyin\":null,\"titleShortPinyin\":null,\"subtitle\":null,\"language\":\"English\",\"price\":520.5,\"translator\":[],\"author\":[\"Steffen Catcherside\"]}],\"totalElements\":1,\"totalPages\":1,\"last\":true,\"number\":0,\"size\":20,\"sort\":null,\"first\":true,\"numberOfElements\":1}"))

    }

    @Test
    fun testBookSearchByFullTitle() {
        mvc!!.perform(get("/api/v1/books?title=consequat in consequat").with(this.authentication()))
                .andExpect(content().json("{\"content\":[{\"isbn\":9785867649253,\"publishDate\":\"2016-07-17\",\"categoryId\":\"TC331C\",\"version\":5,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"consequat in consequat\",\"titlePinyin\":null,\"titleShortPinyin\":null,\"subtitle\":null,\"language\":\"English\",\"price\":85.25,\"translator\":[],\"author\":[\"Lian Hubback\"]}],\"totalElements\":1,\"totalPages\":1,\"last\":true,\"number\":0,\"size\":20,\"sort\":null,\"first\":true,\"numberOfElements\":1}"))
                .andExpect(header().string("X-Total-Count", "1")).andExpect(header().string("Links", "<http://localhost/api/v1/books?page=0&title=consequat in consequat>; rel=\"last\",<http://localhost/api/v1/books?page=0&title=consequat in consequat>; rel=\"first\""))
        mvc!!.perform(get("/api/v1/books?title=consequat&match_all=true").with(this.authentication()))
                .andExpect(header().string("X-Total-Count", "0"))
    }


    @Test
    fun testBookSearchByAuthor() {
        mvc!!.perform(get("/api/v1/books?author=Lian Hubback").with(this.authentication())).andExpect(status().isOk).andExpect(content().json("{\"content\":[{\"isbn\":9785867649253,\"publishDate\":\"2016-07-17\",\"categoryId\":\"TC331C\",\"version\":5,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"consequat in consequat\",\"titlePinyin\":null,\"titleShortPinyin\":null,\"subtitle\":null,\"language\":\"English\",\"price\":85.25,\"author\":[\"Lian Hubback\"],\"translator\":[]}],\"totalElements\":1,\"last\":true,\"totalPages\":1,\"number\":0,\"size\":20,\"sort\":null,\"numberOfElements\":1,\"first\":true}"))
    }

    @Test
    fun testBookSearchByAuthors() {
        mvc!!.perform(get("/api/v1/books?author=Dneig dlsa,Rdlf dls").with(this.authentication())).andExpect(status().isOk).andExpect(content().json("{\"content\":[{\"isbn\":9783158101897,\"publishDate\":\"2016-11-13\",\"categoryId\":\"TC331C\",\"version\":1,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"测试多个作者书籍\",\"titlePinyin\":\"ce,shi,duo,ge,zuo,zhe,shu,ji\",\"titleShortPinyin\":\"csdgzzsj\",\"subtitle\":null,\"language\":\"Chinese\",\"price\":57.22,\"author\":[\"Aneig dlsa\",\"Rdlf dls\",\"Zlicn Tlidb\"],\"translator\":[]},{\"isbn\":9783158101896,\"publishDate\":\"2016-11-13\",\"categoryId\":\"TC331C\",\"version\":1,\"coverUrl\":null,\"preface\":null,\"introduction\":null,\"directory\":null,\"title\":\"测试作者书籍\",\"titlePinyin\":\"ce,shi,zuo,zhe,shu,ji\",\"titleShortPinyin\":\"cszzsj\",\"subtitle\":null,\"language\":\"Chinese\",\"price\":57.22,\"author\":[\"Aneig dlsa\",\"Bianfd sld\",\"Rdlf dls\"],\"translator\":[]}],\"last\":true,\"totalPages\":1,\"totalElements\":2,\"number\":0,\"size\":20,\"sort\":null,\"first\":true,\"numberOfElements\":2}"))
    }

    @Test
    fun testRateLimit() {
        val processor: RequestPostProcessor = RequestPostProcessor { request ->
            request.remoteAddr = "192.168.1.1"
            request
        }
        for (i in 1..5) {
            if (i < 4) {
                mvc!!.perform(get("/api/v1/books?author=Dneig dlsa,Rdlf dls").with(processor)).andExpect(status().isOk)
            } else {
                mvc!!.perform(get("/api/v1/books?author=Dneig dlsa,Rdlf dls").with(processor)).andExpect(status().`is`(429))
            }
        }
    }
}

