package com.athena.service

import com.athena.model.*
import com.athena.repository.jpa.BorrowRepository
import com.athena.repository.jpa.copy.SimpleCopyRepository
import com.athena.security.model.Account
import com.athena.service.util.BorrowVerificationService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/11/10.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
open class BorrowServiceTest {
    @Mock
    lateinit var borrowRepository: BorrowRepository

    @Mock
    lateinit var borrowVerificationService: BorrowVerificationService

    @Mock
    lateinit var simpleCopyRepository: SimpleCopyRepository

    @InjectMocks
    lateinit var borrowService: BorrowService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAdd() {
        val borrow = Borrow()
        this.borrowService.add(borrow)

        verify(this.borrowRepository).save(borrow)
    }

    @Test
    fun testBorrow() {
        val account: Account = mock(Account::class.java)
        val user: User = mock(User::class.java)
        val bookCopy: BookCopy = spy(BookCopy::class.java)
        Mockito.`when`(account.user).thenReturn(user)
        Mockito.`when`(this.borrowRepository.save(Matchers.any(Borrow::class.java))).thenAnswer { invocationOnMock ->
            invocationOnMock.arguments[0]
        }

        val borrow = this.borrowService.borrowCopy(account, bookCopy)

        Assert.assertEquals(borrow.user, user)

    }

    @Test
    fun testReturnCopyByAdmin_ShouldReturnBorrowWithStatusIsAvailable() {
        `when`(this.borrowVerificationService.canReturn(any(Borrow::class.java))).thenReturn(true)
        val user = spy(User::class.java)
        val borrow = spy(Borrow::class.java)
        val account = spy(Account::class.java)
        val copy = spy(SimpleCopy::class.java)
        borrow.copy = copy
        `when`(copy.status).thenReturn(CopyStatus.CHECKED_OUT)
        `when`(account.user).thenReturn(user)
        borrow.user = user
        borrow.enable = true
        `when`(this.simpleCopyRepository.save(any(SimpleCopy::class.java))).thenReturn(copy)
        `when`(this.borrowRepository.findOne(any())).thenReturn(borrow)

        this.borrowService.returnCopy(borrow.id, account)


        verify(copy).status = CopyStatus.AVAILABLE
        verify(borrow).enable = false
    }

    @Test
    fun testReturnCopyBySelf_ShouldReturnBorrowWithStatusIsWaitForVerify() {
        `when`(this.borrowVerificationService.canReturn(any(Borrow::class.java))).thenReturn(true)
        val user = spy(User::class.java)
        val borrow = spy(Borrow::class.java)
        val account = spy(Account::class.java)
        val copy = spy(SimpleCopy::class.java)
        borrow.copy = copy
        `when`(copy.status).thenReturn(CopyStatus.CHECKED_OUT)
        `when`(account.user).thenReturn(user)
        borrow.user = user
        borrow.enable = true
        `when`(this.simpleCopyRepository.save(any(SimpleCopy::class.java))).thenReturn(copy)
        `when`(this.borrowRepository.findOne(any())).thenReturn(borrow)

        this.borrowService.returnCopy(borrow.id, account, true)

        verify(copy).status = CopyStatus.WAIT_FOR_VERIFY
        verify(borrow).enable = false
        verify(copy, never()).status = CopyStatus.AVAILABLE


    }
}