package com.athena.service

import com.athena.exception.http.ResourceNotDeletable
import com.athena.model.borrow.Borrow
import com.athena.model.security.BlockRecord
import com.athena.model.security.NewUserVo
import com.athena.model.security.User
import com.athena.repository.jpa.BlockRecordRepository
import com.athena.repository.jpa.BorrowRepository
import com.athena.repository.jpa.UserRepository
import com.athena.service.security.PrivilegeService
import com.athena.service.security.UserService
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers.any
import org.mockito.Matchers.argThat
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

    @Mock
    lateinit var blockRecordRepository: BlockRecordRepository

    @Mock
    lateinit var borrowRepository: BorrowRepository

    @Mock
    lateinit var privilegeService: PrivilegeService

    @InjectMocks
    lateinit var userService: UserService

    /**
     * Match the user's email is fail@fail.com
     */
    class MatchUserEmail : Matcher<User> {
        override fun _dont_implement_Matcher___instead_extend_BaseMatcher_() {

        }

        override fun describeMismatch(p0: Any?, p1: Description?) {

        }

        override fun matches(p0: Any?): Boolean {
            return p0 is User && p0.email == "fail@fail.com"
        }

        override fun describeTo(p0: Description?) {

        }

    }

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

    @Test
    fun testBlockUser_ShouldBlockTargetUser() {
        val blockedUser = User()
        blockedUser.id = 12L
        val handler = User()
        handler.id = 11L

        `when`(this.blockRecordRepository.save(any(BlockRecord::class.java))).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
        val blockedRecord: BlockRecord = this.userService.blockUser(blockedUser, handler)

        Assert.assertEquals(blockedRecord.blockHandler, handler)
        Assert.assertEquals(blockedRecord.blockedUser, blockedUser)
        Assert.assertNotNull(blockedRecord.id)
        Assert.assertNotNull(blockedRecord.createdAt)
    }

    @Test
    fun testAddUser_ShouldReturnCreatedUser() {
        val newUser = NewUserVo()
        newUser.email = "test@test.com"
        newUser.identity = arrayListOf("ROLE_ADMIN")

        `when`(this.privilegeService.isLegalPrivilege(any(List::class.java as Class<List<String>>))).thenReturn(true)

        this.userService.add(newUser)

        verify(this.privilegeService).isLegalPrivilege(any(List::class.java as Class<List<String>>))
    }

    @Test(expected = ResourceNotDeletable::class)
    fun testDeleteUser_ShouldNotDelete() {
        val borrowedBooks = arrayListOf(Borrow(), Borrow())
        `when`(this.borrowRepository.findAllByUserAndEnableIsTrue(argThat(MatchUserEmail()))).thenReturn(borrowedBooks)
        `when`(this.userRepository.exists(any())).thenReturn(true)

        val user_1 = User()
        user_1.email = "fail@fail.com"
        user_1.id = 1
        val user_2 = User()
        user_2.email = "non@non.com"
        user_2.id = 2
        val users = arrayListOf<User>(user_1, user_2)
        this.userService.delete(users)
    }

    @Test
    fun testDeleteUser_ShouldDelete() {
        val borrows = arrayListOf<Borrow>()
        `when`(this.borrowRepository.findAllByUserAndEnableIsTrue(any())).thenReturn(borrows)
        `when`(this.userRepository.exists(any())).thenReturn(true)
        val user_1 = User()
        user_1.email = "fail@fail.com"
        user_1.id = 1
        val user_2 = User()
        user_2.email = "non@non.com"
        user_2.id = 2
        val users = arrayListOf<User>(user_1, user_2)
        this.userService.delete(users)

        verify(this.userRepository).delete(any(List::class.java as Class<List<User>>))
    }
}