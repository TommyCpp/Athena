package com.athena.controller

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import util.IdentityGenerator

/**
 * Created by Tommy on 2018/1/16.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml", "classpath:user_identity.xml")
@WebAppConfiguration
open class UserControllerTest {
    @Autowired lateinit var context: WebApplicationContext

    lateinit var mvc: MockMvc
    private val identity: IdentityGenerator = IdentityGenerator()
    @Value("\${web.url.prefix}") private var url_prefix: String = ""

    @Before
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()

    }

    @Test
    fun getCurrentUser() {
        this.mvc.perform(MockMvcRequestBuilders.get(url_prefix + "/modifiedUser").with(this.identity.authentication("ROLE_READER"))).andExpect(content().json("{\"id\":1,\"username\":\"reader\",\"wechatId\":\"testWechat\",\"email\":\"test@test.com\",\"identity\":[\"ROLE_READER\"],\"phoneNumber\":\"11111111111\"}"))

    }
}