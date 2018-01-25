package com.athena.controller

import com.athena.repository.jpa.copy.SimpleCopyRepository
import com.athena.repository.mongo.BatchRepository
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
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import util.IdentityGenerator

/**
 * Created by Tommy on 2017/8/25.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml", "classpath:copies.xml", "classpath:user_identity.xml")
@WebAppConfiguration
open class CopyControllerTest {
    @Autowired lateinit var context: WebApplicationContext
    @Autowired lateinit var simpleCopyRepository: SimpleCopyRepository
    @Autowired lateinit var batchRepository: BatchRepository
    lateinit var mvc: MockMvc
    @Value("\${web.url.prefix}") private var url_prefix: String = ""
    private val identity: IdentityGenerator = IdentityGenerator()


    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    fun testGetCopy() {
        this.mvc.perform(MockMvcRequestBuilders.get(this.url_prefix + "/copy/2")
                .with(this.identity.authentication("ROLE_READER"))
        )
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun testDeleteCopies() {
        this.mvc.perform(MockMvcRequestBuilders.delete(this.url_prefix + "/copy/1")
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().is2xxSuccessful)
                .andDo(MockMvcResultHandlers.print())

        Assert.assertNull(this.simpleCopyRepository.findOne(1L))
    }


    @Test
    fun testPartialUpdate() {
        this.mvc.perform(MockMvcRequestBuilders.patch(this.url_prefix + "/copy/1").content("{\"status\":5}").contentType(MediaType.APPLICATION_JSON)
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().is2xxSuccessful)
                .andDo(MockMvcResultHandlers.print())

        val status = this.simpleCopyRepository.findOne(1L).status
        Assert.assertEquals(5, status)
    }
}