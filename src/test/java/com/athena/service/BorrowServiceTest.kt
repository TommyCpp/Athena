package com.athena.service

import com.athena.model.BookCopy
import com.athena.model.Borrow
import com.athena.model.User
import com.athena.repository.jpa.BorrowRepository
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
    lateinit var borrowVerrificationService: BorrowVerificationService

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

        val borrow = this.borrowService.borrowBook(account, bookCopy)

        Assert.assertEquals(borrow.user, user)

    }
}