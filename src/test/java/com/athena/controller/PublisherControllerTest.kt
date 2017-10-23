package com.athena.controller

import com.athena.repository.jpa.PublisherRepository
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by Tommy on 2017/10/23.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml")
@WebAppConfiguration
class PublisherControllerTest {
    @Autowired private val context: WebApplicationContext? = null
    @Autowired private lateinit var publisherRepository: PublisherRepository
    @Autowired private val batchRepository: BatchRepository? = null
    private lateinit var mvc: MockMvc
    @Value("\${web.url.prefix}") private var url_prefix: String = ""
    private val identity: IdentityGenerator = IdentityGenerator()


    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    fun testGet(){
        this.mvc.perform(MockMvcRequestBuilders.get(this.url_prefix + "/publishers/127")
                .with(this.identity.authentication("ROLE_READER"))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":\"127\",\"name\":\"TestDll Publisher\",\"location\":\"NewYork\"}"))

    }

    @Test
    fun testSave(){
        this.mvc.perform(MockMvcRequestBuilders.post(this.url_prefix + "/publishers").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"id\":\"129\",\"name\":\"TestDll Publisher\",\"location\":\"NewYork\"}")
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(MockMvcResultMatchers.status().`is`(201))
                .andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun testPartialUpdate(){
        this.mvc.perform(MockMvcRequestBuilders.patch(this.url_prefix + "/publishers/127").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"name\":\"ChangedName\"}")
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val publisher = this.publisherRepository.findOne("127")
        Assert.assertEquals(publisher.name,"ChangedName")

    }

    @Test
    fun testDelete(){
        this.mvc.perform(MockMvcRequestBuilders.delete(this.url_prefix+"/publishers/128").contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent)
    }


}