package com.athena.model

import com.athena.repository.UserRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener

import javax.transaction.Transactional

/**
 * Created by tommy on 2017/3/27.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:users.xml")
open class UserTest {
    @Autowired private var repository:UserRepository? = null

    @Autowired private var passwordEncoder:PasswordEncoder? = null

    @Test
    fun testListener(){
        var user:User = User()
        user.password = "123456"
        user.email = "test"
        user.identity = "ADMIN"
        user.username = "testUser"
        user.wechatId = "sadf"

        repository!!.save(user)
        Assert.assertTrue(passwordEncoder!!.matches("123456", user.password))

        user.email = "test@test.com"
        repository!!.save(user)
        Assert.assertTrue(passwordEncoder!!.matches("123456", user.password))

    }
}