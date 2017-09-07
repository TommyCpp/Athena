package com.athena.controller

import com.athena.repository.jpa.SimpleCopyRepository
import com.athena.repository.mongo.BatchRepository
import com.athena.util.IdentityGenerator
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by Tommy on 2017/8/25.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml", "classpath:copies.xml")
@WebAppConfiguration
open class CopyControllerTest {
    @Autowired private val context: WebApplicationContext? = null
    @Autowired private val simpleCopyRepository: SimpleCopyRepository? = null
    @Autowired private val batchRepository: BatchRepository? = null
    private var mvc: MockMvc? = null
    @Value("\${web.url.prefix}") private var url_prefix: String = ""
    private val identity: IdentityGenerator = IdentityGenerator()


    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    fun testGetCopy() {
        this.mvc!!.perform(MockMvcRequestBuilders.get(this.url_prefix + "/copy/2")
                .with(this.identity.authentication("ROLE_READER"))
        )
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun testDeleteCopies() {
        this.mvc!!.perform(MockMvcRequestBuilders.delete(this.url_prefix + "/copy/1")
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())

        Assert.assertNull(this.simpleCopyRepository!!.findOne(1L))
    }
}