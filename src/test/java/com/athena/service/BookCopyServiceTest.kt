package com.athena.service

import com.athena.exception.IdOfResourceNotFoundException
import com.athena.exception.IsbnAndCopyIdMismatchException
import com.athena.exception.MixedCopyTypeException
import com.athena.model.Book
import com.athena.model.BookCopy
import com.athena.model.CopyStatus
import com.athena.repository.jpa.BookCopyRepository
import com.athena.repository.jpa.BookRepository
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
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import javax.transaction.Transactional

/**
 * Created by Tommy on 2017/9/7.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml")
open class BookCopyServiceTest {
    @Qualifier("copyService")
    @Autowired private var copyService: CopyService? = null
    @Autowired private var bookCopyService: BookCopyService? = null
    @Autowired private var journalCopyService: JournalCopyService? = null
    @Autowired private var bookCopyRepository: BookCopyRepository? = null
    @Autowired private var journalCopyRepository: JournalCopyRepository? = null
    @Autowired private var simpleCopyRepository: SimpleCopyRepository? = null
    @Autowired private var bookRepository: BookRepository? = null


    @Test
    fun testDeleteCopyByIsbn() {
        this.bookCopyService!!.deleteCopies(9783158101895L)
        var book: Book = this.bookRepository!!.findOne(9783158101895L)
        Assert.assertEquals(0, this.bookCopyRepository!!.findByBook(book).size)
    }


    @Test(expected = IdOfResourceNotFoundException::class)
    fun testDeleteByIsbnException() {
        this.bookCopyService!!.deleteCopies(111111111111111111L)
    }

    @Test(expected = MixedCopyTypeException::class)
    fun testTriggerMixedCopyTypeException() {
        var copyIdList = arrayListOf(1L, 2L, 3L)
        this.bookCopyService!!.deleteCopies(copyIdList)
    }

    @Test(expected = IsbnAndCopyIdMismatchException::class)
    fun testTriggerIsbnAndCopyIdMismatchException() {
        this.bookCopyService!!.deleteCopy(9783158101895L, 1L)
    }


    @Test
    fun testUpdateBookCopy(){
        var copy1 = BookCopy()
        copy1.id = 1L
        copy1.status = CopyStatus.BOOKED

        var copy2 = BookCopy()
        copy2.id = 2L
        copy2.status = CopyStatus.BOOKED

        var copyList: List<BookCopy> = arrayListOf(copy1, copy2)

        this.bookCopyService!!.updateCopies(copyList)

        Assert.assertEquals(CopyStatus.BOOKED, this.bookCopyRepository!!.findOne(1L).status)
    }

}