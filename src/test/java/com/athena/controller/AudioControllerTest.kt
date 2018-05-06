package com.athena.controller

import com.athena.model.security.Account
import com.athena.model.security.JwtAuthenticationToken
import com.athena.model.security.User
import com.athena.repository.jpa.AudioRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by 吴钟扬 on 2018/3/30.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(TransactionalTestExecutionListener::class, DbUnitTestExecutionListener::class, DependencyInjectionTestExecutionListener::class)
@DatabaseSetup("classpath:audios.xml", "classpath:publishers.xml", "classpath:users.xml")
@WebAppConfiguration
class AudioControllerTest {
    @Autowired
    private val context: WebApplicationContext? = null
    @Autowired
    private val applicationContext: ApplicationContext? = null
    @Autowired
    private val audioRepository: AudioRepository? = null
    @Value("\${web.url.prefix}")
    private val urlPrefix: String? = null

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    private fun createAuthentication(role: String): JwtAuthenticationToken {
        val encoder = BCryptPasswordEncoder()
        val user = User()
        user.identity = arrayListOf(role)
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

        return SecurityMockMvcRequestPostProcessors.securityContext(securityContext)
    }

    @Test
    fun testSearch() {
        this.mvc.perform(get(this.urlPrefix + "/audios?language=Spanish").with(authentication("ROLE_READER")))
                .andDo(print())
                .andExpect(content().json(
                "{\"content\":[{\"isrc\":\"CNM010100303\",\"title\":\"ForSearch\",\"subtitle\":null,\"author\":[\"searchdll\"],\"translator\":[],\"publishDate\":\"2018-01-13\",\"coverUrl\":null,\"price\":88.23,\"titlePinyin\":null,\"titleShortPinyin\":null,\"language\":\"Spanish\",\"publisher\":{\"id\":\"127\",\"name\":\"TestDll Publisher\",\"location\":\"NewYork\"}}],\"totalElements\":1,\"totalPages\":1,\"last\":true,\"number\":0,\"size\":20,\"sort\":null,\"first\":true,\"numberOfElements\":1}",
                false
        ))
    }


}