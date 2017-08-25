package com.athena.controller

import com.athena.model.CopyPK
import com.athena.repository.jpa.CopyRepository
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
import java.util.*

/**
 * Created by Tommy on 2017/8/25.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml")
@WebAppConfiguration
open class CopyControllerTest {
    @Autowired private val context: WebApplicationContext? = null
    @Autowired private val copyRepository: CopyRepository? = null
    private var mvc: MockMvc? = null
    @Value("\${web.url.prefix}") private var url_prefix: String = ""
    private val identity: IdentityGenerator = IdentityGenerator()


    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    fun testCreateCopy() {
        /**
         * Test create copies
         * */
        var isbn = "9783158101900"
        var copyList: ArrayList<CopyPK> = arrayListOf()
        for (i in 1..4) {
            copyList.add(CopyPK(isbn.toLong(), i))
        }

        this.mvc!!.perform(post(this.url_prefix + "/copy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(copyList))
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isOk)

        Assert.assertNotNull(this.copyRepository!!.findOne(CopyPK(isbn.toLong(), 1)))


        /**
         * Test exception
         * 1. Book do not exist
         * */
        var nonExistIsbn = "9769438101900"
        copyList = arrayListOf()
        for (i in 1..2) {
            copyList.add(CopyPK(nonExistIsbn.toLong(), i))
        }

        this.mvc!!.perform(post(this.url_prefix + "/copy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(ObjectMapper().writeValueAsString(copyList))
                .with(this.identity.authentication("ROLE_ADMIN"))
        )
                .andExpect(status().isNotFound)



    }
}