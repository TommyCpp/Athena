package com.athena.controller

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import util.IdentityGenerator

/**
 * Created by Tommy on 2018/7/10.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:users.xml", "classpath:blocks.xml")
@WebAppConfiguration
open class AdminControllerTest {
    @Autowired
    private val context: WebApplicationContext? = null
    @Autowired
    private val applicationContext: ApplicationContext? = null

    @Value("\${web.url.prefix}")
    private val urlPrefix: String? = null

    lateinit var mvc: MockMvc
    private val identity: IdentityGenerator = IdentityGenerator()


    @Before
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    fun testGetUser_ShouldReturnUserInfo() {
        this.mvc.perform(
                get(this.urlPrefix + "/users/11")
                        .with(identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isOk)
                .andExpect(content().json("{\"id\":11,\"username\":\"test\",\"wechatId\":\"cha\",\"email\":\"sldjf@sldfj.com\",\"identity\":[\"ROLE_READER\"],\"phoneNumber\":\"1526421812\",\"isBlocked\":true}", false))
    }

    @Test
    fun testGetUsers_ShouldReturnUsersByIds() {
        this.mvc.perform(
                get(this.urlPrefix + "/users")
                        .param("id", "11")
                        .param("id", "12")
                        .with(identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isOk)
                .andExpect(content().json("[{\"id\":11,\"username\":\"test\",\"wechatId\":\"cha\",\"email\":\"sldjf@sldfj.com\",\"identity\":[\"ROLE_READER\"],\"phoneNumber\":\"1526421812\",\"isBlocked\":true},{\"id\":12,\"username\":\"testUser\",\"wechatId\":\"chasdf\",\"email\":\"ssdfsdff@sldfj.com\",\"identity\":[\"ROLE_READER\"],\"phoneNumber\":\"18554692356\",\"isBlocked\":false}]", false))
    }

    @Test
    fun testGetUsers_ShouldThrowUnsupportedParamException() {
        this.mvc.perform(
                get(this.urlPrefix + "/users")
                        .param("id", "11")
                        .param("whatever", "test")
                        .with(identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun testAddUsers_ShouldReturnCreatedUser(){
        this.mvc.perform(
                post(this.urlPrefix + "/users")
                        .content("{\"username\": \"newUser\",\"password\": \"123456\",\"wechatId\": \"test\",\"email\":\"test@test.com\",\"phoneNumber\":\"18554125221\",\"identity\":[\"ROLE_READER\"]}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .with(identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().`is`(201))

    }
}