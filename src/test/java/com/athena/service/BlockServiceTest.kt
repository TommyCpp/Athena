package com.athena.service

import com.athena.model.BlockedUser
import com.athena.model.User
import com.athena.repository.jpa.BlockedUserRepository
import com.athena.repository.jpa.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * Created by Tommy on 2017/11/28.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class BlockServiceTest {
    @Mock
    lateinit var blockedUserRepository: BlockedUserRepository

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var blockService: BlockService

    class HandlerNotNullMatcher : ArgumentMatcher<BlockedUser>() {
        override fun matches(p0: Any?): Boolean {
            if(p0 is BlockedUser){
                return p0.handler != null
            }
            return false
        }

    }

    private var user: User = User()

    private var handler: User = User()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        user.id = 3L
        user.email = "test@test.com"

        handler.id = 4L
        handler.email = "test@admin.com"
        handler.identity = "ADMIN"
    }

    @Test
    fun testBlockUser_ShouldBlockTheUser() {
        Mockito.`when`(this.userRepository.findOne(any())).thenReturn(user)

        this.blockService.blockUser(1L, handler)

        verify(this.userRepository).findOne(any())
        verify(this.blockedUserRepository).save(Matchers.argThat(HandlerNotNullMatcher()))

    }

    @Test
    fun testUnblockUser_ShouldUnblock(){
        val blockedUser = BlockedUser(user)
        `when`(this.blockedUserRepository.findOne(any())).thenReturn(blockedUser)

        this.blockService.unBlockUser(3L, handler)

        verify(this.blockedUserRepository).delete(blockedUser)
        verify(this.blockedUserRepository).findOne(3L)
    }
}