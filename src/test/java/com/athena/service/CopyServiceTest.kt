package com.athena.service

import com.athena.repository.jpa.BookCopyRepository
import com.athena.repository.jpa.JournalCopyRepository
import com.athena.repository.jpa.SimpleCopyRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml", "classpath:copies.xml")
open class CopyServiceTest {
    @Qualifier("copyService")
    @Autowired private var simpleCopyService: CopyService? = null
    @Autowired private var bookCopyService: BookCopyService? = null
    @Autowired private var journalCopyService: JournalCopyService? = null
    @Autowired private var bookCopyRepository: BookCopyRepository? = null
    @Autowired private var journalCopyRepository: JournalCopyRepository? = null
    @Autowired private var simpleCopyRepository: SimpleCopyRepository? = null


    @Test
    fun testGetCopy() {
        Assert.assertTrue(journalCopyRepository!!.findOne(3L).equals(journalCopyService!!.getCopy(3L)))
        Assert.assertTrue(bookCopyRepository!!.findOne(1L).equals(bookCopyService!!.getCopy(1L)))
    }

    @Test
    fun testDeleteCopyFromSimpleCopyService() {
        this.simpleCopyService!!.deleteCopy(1L)
        Assert.assertNull(this.bookCopyRepository!!.findOne(1L))
    }

    @Test
    fun testDeleteCopy() {
        this.bookCopyService!!.deleteCopies(arrayListOf(1L, 2L))
        this.journalCopyService!!.deleteCopy(3L)

        Assert.assertNull(this.bookCopyRepository!!.findOne(1L))
        Assert.assertNull(this.bookCopyRepository!!.findOne(2L))
        Assert.assertNull(this.journalCopyRepository!!.findOne(3L))

    }

    @Test(expected = EmptyResultDataAccessException::class)
    fun testDeleteCopyException() {
        /**
         * Test Exception
         *
         * 1. test using BookCopyService delete journalCopy
         * */
        this.bookCopyService!!.deleteCopy(3L)

        Assert.assertTrue(true)

    }

    @Test(expected = EmptyResultDataAccessException::class)
    fun testDeleteCopiesException() {

        /**
         * Test Exception
         *
         * 2. test using JournalCopyService delete multiple copy
         * */
        this.journalCopyService!!.deleteCopies(arrayListOf(1L, 2L))

        Assert.assertTrue(true)

    }
}