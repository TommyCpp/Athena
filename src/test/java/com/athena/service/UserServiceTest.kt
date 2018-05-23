package com.athena.service

import com.athena.model.security.User
import com.athena.repository.jpa.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by tommy on 2017/5/20.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
open class UserServiceTest {
    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userService: UserService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val user11 = User()
        user11.id = 11L
        user11.username = "test11"
        user11.wechatId = "whatever"

        `when`(this.userRepository.findOne(11L)).thenReturn(user11)
    }

    @Test
    fun testGetUser() {
        val user = this.userService.get(11L)

        verify(this.userRepository).findOne(11L)

        Assert.assertNotNull(user)
        Assert.assertEquals("test11",user.username)
    }

    //todo: keep test
}