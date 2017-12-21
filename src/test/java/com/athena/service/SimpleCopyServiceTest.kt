package com.athena.service

import com.athena.exception.http.MixedCopyTypeException
import com.athena.model.CopyStatus
import com.athena.model.SimpleCopy
import com.athena.model.User
import com.athena.repository.jpa.copy.BookCopyRepository
import com.athena.repository.jpa.copy.JournalCopyRepository
import com.athena.repository.jpa.copy.SimpleCopyRepository
import com.athena.security.model.Account
import com.athena.service.borrow.PublicationDamagedHandler
import com.athena.service.copy.BookCopyService
import com.athena.service.copy.JournalCopyService
import com.athena.service.copy.SimpleCopyService
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import javax.transaction.Transactional

/**
 * Created by Tommy on 2017/9/2.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml", "classpath:copies.xml", "classpath:journal_copy.xml", "classpath:book_copy.xml","classpath:user_identity.xml")
open class SimpleCopyServiceTest {
    @Autowired private var simpleCopyService: SimpleCopyService? = null
    @Autowired private var bookCopyService: BookCopyService? = null
    @Autowired private var journalCopyService: JournalCopyService? = null
    @Autowired private var bookCopyRepository: BookCopyRepository? = null
    @Autowired private var journalCopyRepository: JournalCopyRepository? = null
    @Autowired private var simpleCopyRepository: SimpleCopyRepository? = null


    @Test
    fun testGetCopy() {
        Assert.assertTrue(journalCopyRepository!!.findOne(3L).equals(journalCopyService!!.get(3L)))
        Assert.assertTrue(bookCopyRepository!!.findOne(1L).equals(bookCopyService!!.get(1L)))
    }

    @Test
    fun testDeleteCopyFromSimpleCopyService() {
        this.simpleCopyService!!.deleteById(1L)
        Assert.assertNull(this.bookCopyRepository!!.findOne(1L))
    }

    @Test
    fun testDeleteCopy() {
        this.journalCopyService!!.deleteById(3L)

        Assert.assertNull(this.journalCopyRepository!!.findOne(3L))

    }

    @Test(expected = EmptyResultDataAccessException::class)
    fun testDeleteCopyException() {
        /**
         * Test Exception
         *
         * 1. test using BookCopyService delete journalCopy
         * */
        this.bookCopyService!!.deleteById(3L)

        Assert.assertTrue(true)

    }

    @Test(expected = MixedCopyTypeException::class)
    fun testDeleteCopiesException() {

        /**
         * Test Exception
         *
         * 2. test using JournalCopyService delete multiple copy
         * */
        this.journalCopyService!!.deleteById(arrayListOf(1L, 2L))

        Assert.assertTrue(true)
    }

    @Test
    fun testUpdateCopies() {
        var copy1 = SimpleCopy()
        copy1.id = 6
        copy1.status = CopyStatus.BOOKED
        var copyList = arrayListOf(copy1)

        this.simpleCopyService!!.update(copyList)

        Assert.assertEquals(CopyStatus.BOOKED, this.simpleCopyRepository!!.findOne(6L).status)
    }

    @Test
    fun testVerifyReturnedCopy_ShouldSetCopyStatusToDAMAGED() {
        val simpleCopyRepository = mock(SimpleCopyRepository::class.java)
        val simpleCopyService = SimpleCopyService(simpleCopyRepository, mock(PublicationDamagedHandler::class.java))
        val account = mock(Account::class.java)
        `when`(account.user).thenReturn(User())
        val simpleCopy = mock(SimpleCopy::class.java)
        `when`(simpleCopyRepository.save(any(SimpleCopy::class.java))).then { invocationOnMock -> invocationOnMock.getArgumentAt(0, SimpleCopy::class.java) }

        simpleCopyService.verifyReturnedCopy(account, simpleCopy, true)

        verify(simpleCopy).status = CopyStatus.DAMAGED

    }


}