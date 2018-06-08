package com.athena.service

import com.athena.model.security.User
import com.athena.repository.jpa.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers.any
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
        `when`(this.userRepository.exists(11L)).thenReturn(true)
        `when`(this.userRepository.save(any(User::class.java))).thenAnswer { invocationOnMock ->
            invocationOnMock.arguments[0]
        }
    }

    @Test
    fun testGetUser() {
        val user = this.userService.get(11L)

        verify(this.userRepository).findOne(11L)

        Assert.assertNotNull(user)
        Assert.assertEquals("test11", user.username)
    }

    @Test
    fun testUpdateUser() {
        val updateUser = User()
        updateUser.id = 11
        updateUser.username = "updatetest11"
        val user = this.userService.update(updateUser)

        verify(this.userRepository).exists(updateUser.id)

        Assert.assertEquals("updatetest11", user.username)

    }

    @Test
    fun testDeleteUser() {
        val user = User()
        user.id = 11L
        this.userService.delete(user)

        verify(this.userRepository).delete(user)
    }

    @Test
    fun testAddUser() {
        val user = User()
        user.id = 12L
        val savedUser = this.userService.add(user)

        verify(this.userRepository).save(user)

        Assert.assertEquals(12L, savedUser.id)
    }
}