package com.athena.controller

import com.athena.repository.jpa.BookRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.lordofthejars.nosqlunit.annotation.UsingDataSet
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import util.IdentityGenerator

/**
 * Created by Tommy on 2017/8/19.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@WebAppConfiguration
open class BatchControllerTest {
    @Autowired private val context: WebApplicationContext? = null
    @Autowired private val applicationContext: ApplicationContext? = null
    @Autowired private val bookRepository: BookRepository? = null

    private var mvc: MockMvc? = null
    @Value("\${web.url.prefix}") private var url_prefix: String = ""
    private val identity: IdentityGenerator = IdentityGenerator()

    @get:Rule
    var mongoRule: MongoDbRule = MongoDbRule.MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb("Athena")


    @Before
    fun setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    @UsingDataSet(locations = arrayOf("/batch.json"), loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    fun testGetBatch() {
        /**
         * Test the existing batch
         * */
        this.mvc!!.perform(get(this.url_prefix + "/batch/bac0546a-9001-4f65-8417-c6acdc7d5e6c").with(identity.authentication("ROLE_ADMIN")))
                .andExpect(status().isOk)

        /**
         * Test the non-existing batch
         * */
        this.mvc!!.perform(get(this.url_prefix + "/batch/test").with(identity.authentication("ROLE_ADMIN")))
                .andExpect(status().isNotFound)


        /**
         * Test privilege
         * 1. The reader should not be allowed to check the book batch
         * */
        this.mvc!!.perform(get(this.url_prefix + "/batch/bac0546a-9001-4f65-8417-c6acdc7d5e6c").with(identity.authentication("ROLE_READER")))
                .andExpect(status().isNotFound)

    }


}