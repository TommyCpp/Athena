package com.athena.service.security

import com.athena.model.security.BlockRecord
import com.athena.model.security.User
import com.athena.repository.jpa.BlockRecordRepository
import com.athena.repository.jpa.UserRepository
import com.athena.service.borrow.BlockService
import org.junit.Assert
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
    lateinit var BlockRecordRepository: BlockRecordRepository

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var blockService: BlockService

    class HandlerNotNullMatcher : ArgumentMatcher<BlockRecord>() {
        override fun matches(p0: Any?): Boolean {
            if (p0 is BlockRecord) {
                return p0.blockHandler != null
            }
            return false
        }

    }

    private var user: User = User()

    private var handler: User = User()

    private var unblockHandler: User = User()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        user.id = 3L
        user.email = "test@test.com"

        handler.id = 4L
        handler.email = "test@admin.com"
        handler.setIdentity("ROLE_ADMIN")

        unblockHandler.id = 6L
        unblockHandler.email = "unblock@admin.com"
        unblockHandler.setIdentity("ROLE_SUPERADMIN")

    }

    @Test
    fun testBlockUser_ShouldBlockTheUser() {
        Mockito.`when`(this.userRepository.findOne(any())).thenReturn(user)

        this.blockService.blockUser(1L, handler)

        verify(this.userRepository).findOne(any())
        verify(this.BlockRecordRepository).save(Matchers.argThat(HandlerNotNullMatcher()))

    }

    @Test
    fun testUnblockUser_ShouldUnblock() {
        val blockRecord = BlockRecord()
        val mockBlockUser = mock(User::class.java)
        blockRecord.blockHandler = handler
        blockRecord.blockedUser = mockBlockUser
        blockRecord.enabled = true
        `when`(mockBlockUser.blockRecords).thenReturn(arrayListOf(blockRecord))
        `when`(mockBlockUser.id).thenReturn(12L)
        `when`(this.BlockRecordRepository.findOne(any())).thenReturn(blockRecord)
        `when`(this.userRepository.findOne(12L)).thenReturn(mockBlockUser)

        this.blockService.unBlockUser(12L, unblockHandler)

        Assert.assertEquals(false, blockRecord.enabled)
        Assert.assertEquals(unblockHandler, blockRecord.unblockHandler)
        verify(this.userRepository).save(mockBlockUser)
    }
}