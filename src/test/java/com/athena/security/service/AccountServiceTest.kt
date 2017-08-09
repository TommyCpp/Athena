package com.athena.security.service

import com.athena.model.User
import com.athena.repository.UserRepository
import com.athena.security.model.Account
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional

/**
 * Created by tommy on 2017/3/27.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
open class AccountServiceTest {

    @MockBean private val accountService: AccountService? = null

    private var user: User? = null

    @Autowired private val userRepository: UserRepository? = null

    @Before fun setup() {
        val user = User()
        user.username = "TestUser"
        user.email = "test@test.com"
        user.identity = "ADMIN"
        user.password = "123456"
        user.wechatId = "testtest"
        this.user = user
        userRepository!!.save(user)
    }

    @Test fun testLoadUser() {
        given(this.accountService!!.loadAccountById(this.user!!.id)).willReturn(Account(this.user))
    }
}
