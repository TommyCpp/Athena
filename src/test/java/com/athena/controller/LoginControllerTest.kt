package com.athena.controller

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by Tommy on 2017/6/30.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml")
@WebAppConfiguration
open class LoginControllerTest{
    @Autowired private val context: WebApplicationContext? = null

    private var mvc: MockMvc? = null

    @Before fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()

    }

    /*
    * Using chars as id to try to login
    * */
    @Test fun testLoginWithInvalidId(){
        var result = mvc!!.perform(post("/login").content("{id=test,password=test}")).andExpect(status().is4xxClientError).andReturn()
        var stringResult = result.response.errorMessage
        Assert.assertTrue(stringResult.contains("The id should be numbers"))
    }
}