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
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml", "classpath:copies.xml")
open class BookCopyServiceTest {
    @Autowired private lateinit var simpleCopyService: SimpleCopyService
    @Autowired private lateinit var bookCopyService: BookCopyService
    @Autowired private lateinit var journalCopyService: JournalCopyService
    @Autowired private lateinit var bookCopyRepository: BookCopyRepository
    @Autowired private lateinit var journalCopyRepository: JournalCopyRepository
    @Autowired private lateinit var simpleCopyRepository: SimpleCopyRepository
    @Autowired private lateinit var bookRepository: BookRepository

    @Test
    fun testAddCopy() {
        var copy = BookCopy()
        var book = this.bookRepository.findOne(9786395132407L)
        copy.book = book
        copy.status = CopyStatus.BOOKED
        this.bookCopyService.addCopy(copy)

        Assert.assertNotNull(this.bookCopyRepository.findOne(copy.id))

    }

    @Test
    fun testDeleteCopyByIsbn() {
        this.bookCopyService.deleteCopies(9783158101895L)
        var book: Book = this.bookRepository.findOne(9783158101895L)
        Assert.assertEquals(0, this.bookCopyRepository.findByBook(book).size)
    }


    @Test(expected = IdOfResourceNotFoundException::class)
    fun testDeleteByIsbnException() {
        this.bookCopyService.deleteCopies(111111111111111111L)
    }

    @Test(expected = MixedCopyTypeException::class)
    fun testTriggerMixedCopyTypeException() {
        var copyIdList = arrayListOf(1L, 2L, 3L) //Mixed Type
        this.bookCopyService.deleteCopies(copyIdList)
    }

    @Test(expected = IsbnAndCopyIdMismatchException::class)
    fun testTriggerIsbnAndCopyIdMismatchException() {
        this.bookCopyService.deleteCopy(9783158101895L, 1L)
    }


    @Test
    fun testUpdateBookCopy() {
        var copy1 = this.bookCopyRepository.findByIdAndBookIsNotNull(1L)
        copy1.status = CopyStatus.BOOKED

        var copy2 = this.bookCopyRepository.findByIdAndBookIsNotNull(1L)
        copy2.status = CopyStatus.BOOKED

        var copyList: List<BookCopy> = arrayListOf(copy1, copy2)

        this.bookCopyService.updateCopies(copyList)

        Assert.assertEquals(CopyStatus.BOOKED, this.bookCopyRepository.findByIdAndBookIsNotNull(1L).status)
    }


    /**
     * Test update a journal-copy with BookCopyService
     *
     * Should change nothing
     * */
    @Test
    fun testUpdateOtherKindCopy() {

        var copy = BookCopy()
        copy.id = 3L
        copy.book = this.bookRepository.findOne(9783158101895L)
        copy.status = CopyStatus.DAMAGED

        this.bookCopyService.updateCopy(copy)

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.journalCopyRepository.findOne(3L).status)

        this.bookCopyService.updateCopies(arrayListOf(copy))

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.journalCopyRepository.findOne(3L).status)

    }

}