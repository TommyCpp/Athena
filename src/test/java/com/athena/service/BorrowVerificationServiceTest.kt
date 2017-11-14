package com.athena.service

import com.athena.model.Borrow
import com.athena.model.CopyStatus
import com.athena.model.SimpleCopy
import com.athena.model.User
import com.athena.repository.jpa.BorrowRepository
import com.athena.repository.jpa.copy.SimpleCopyRepository
import com.athena.security.model.Account
import com.athena.service.util.BorrowVerificationService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * Created by Tommy on 2017/11/14.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class BorrowVerificationServiceTest {

    lateinit var borrowRepository: BorrowRepository
    lateinit var simpleCopyRepository: SimpleCopyRepository
    lateinit var borrowVerificationService: BorrowVerificationService

    @Before
    fun setup() {
        this.borrowRepository = mock(BorrowRepository::class.java)
        this.simpleCopyRepository = mock(SimpleCopyRepository::class.java)
        this.borrowVerificationService = BorrowVerificationService(this.borrowRepository, this.simpleCopyRepository, 4)

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testUserCanBorrow_ShouldReturnTrue() {
        val account = mock(Account::class.java)
        `when`(account.user).thenReturn(User())
        `when`(this.borrowRepository.findAllByUserAndEnableIsTrue(any(User::class.java))).thenReturn(arrayListOf(Borrow(), Borrow(), Borrow(), Borrow()))
        val result = this.borrowVerificationService.userCanBorrow(account)

        verify(this.borrowRepository).findAllByUserAndEnableIsTrue(any())
        Assert.assertTrue(result)
    }

    @Test
    fun testCopyCanBorrow_ShouldReturnTrue() {
        val simpleCopy = mock(SimpleCopy::class.java)
        `when`(simpleCopy.status).thenReturn(CopyStatus.AVAILABLE)
        `when`(this.simpleCopyRepository.findOne(any())).thenReturn(simpleCopy)

        val result = this.borrowVerificationService.copyCanBorrow(simpleCopy)
        Assert.assertTrue(result)
    }

    @Test
    fun testCanReturn_ShouldReturnFalse() {
        val borrow = mock(Borrow::class.java)
        `when`(borrow.enable).thenReturn(false)
        `when`(borrow.id).thenReturn("1")

        val optional: Optional<Borrow> = Optional.ofNullable(null)
        `when`(this.borrowRepository.findFirstByIdAndEnable(any(),eq(true))).thenReturn(optional)

        val result_1 = this.borrowVerificationService.canReturn(borrow)
        Assert.assertFalse(result_1)

        `when`(borrow.enable).thenReturn(true)
        val result_2 = this.borrowVerificationService.canReturn(borrow)

        verify(this.borrowRepository).findFirstByIdAndEnable(any(), eq(true))
        Assert.assertFalse(result_2)

    }
}