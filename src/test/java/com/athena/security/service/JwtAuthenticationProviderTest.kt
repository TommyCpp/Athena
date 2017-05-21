package com.athena.security.service

import com.athena.model.User
import com.athena.repository.UserRepository
import com.athena.security.model.Account
import com.athena.security.model.JwtAuthenticationToken
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional

/**
 * Created by Tommy on 2017/3/26.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
open class JwtAuthenticationProviderTest {

    private var jwtAuthenticationToken: JwtAuthenticationToken? = null

    private var jwtAuthenticationProvider: JwtAuthenticationProvider? = null

    @Autowired private val userRepository: UserRepository? = null
    @Autowired private val service: AccountService? = null
    @Autowired private val passwordEncoder: PasswordEncoder? = null
    private var tester: User? = null

    @Before fun setup() {
        this.tester = User()
        tester!!.wechatId = "test"
        tester!!.username = "test"
        tester!!.identity = "ADMIN"
        tester!!.password = "123456"
        tester!!.email = "test@test.com"
        userRepository!!.save<User>(tester)
        jwtAuthenticationToken = JwtAuthenticationToken(tester)
        jwtAuthenticationProvider = JwtAuthenticationProvider(service, passwordEncoder)
    }

    @Test fun testAuthenticate() {
        val user = User()
        user.id = tester!!.id
        user.password = "123456"
        val result = jwtAuthenticationProvider!!.authenticate(JwtAuthenticationToken(user))
        Assert.assertTrue(result.isAuthenticated)
        Assert.assertEquals(result.principal, Account(this.tester))
    }

}
