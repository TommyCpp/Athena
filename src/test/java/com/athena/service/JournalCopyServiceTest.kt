package com.athena.service

import com.athena.exception.http.IdOfResourceNotFoundException
import com.athena.exception.http.MixedCopyTypeException
import com.athena.model.CopyStatus
import com.athena.model.JournalCopy
import com.athena.model.JournalPK
import com.athena.repository.jpa.JournalRepository
import com.athena.repository.jpa.copy.BookCopyRepository
import com.athena.repository.jpa.copy.JournalCopyRepository
import com.athena.service.copy.JournalCopyService
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
 * Created by 吴钟扬 on 2017/9/12.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml", "classpath:copies.xml")
open class JournalCopyServiceTest {
    @Qualifier("journalRepository")
    @Autowired lateinit var journalRepository: JournalRepository

    @Qualifier("journalCopyService")
    @Autowired lateinit var journalCopyService: JournalCopyService

    @Qualifier("journalCopyRepository")
    @Autowired lateinit var journalCopyRepository: JournalCopyRepository

    @Autowired lateinit var bookCopyRepository: BookCopyRepository

    @Test
    fun testAddCopy() {
        var journalCopy = JournalCopy()
        val journalPK = JournalPK()
        journalPK.issn = "03718472"
        journalPK.year = 2014
        journalPK.index = 23
        journalCopy.journal = journalRepository.findOne(journalPK)
        journalCopy.status = CopyStatus.BOOKED
        this.journalCopyService.addCopy(journalCopy)

        Assert.assertNotNull(journalCopyRepository.findOne(journalCopy.id))
    }

    @Test
    fun testAddCopies() {
        var journalCopyList = arrayListOf<JournalCopy>()
        val journalPK = JournalPK()
        journalPK.issn = "03718472"
        journalPK.year = 2014
        journalPK.index = 23
        var journal = journalRepository.findOne(journalPK)

        for (i in 1..3) {
            var journalCopy = JournalCopy()
            journalCopy.journal = journal
            journalCopy.status = CopyStatus.CHECKED_OUT
            journalCopyList.add(journalCopy)
        }

        this.journalCopyService.addCopies(journalCopyList)

        Assert.assertNotNull(journalCopyRepository.findOne(journalCopyList[0].id))
        Assert.assertNotNull(journalCopyRepository.findOne(journalCopyList[1].id))
    }


    @Test
    fun testGetCopy() {
        Assert.assertEquals(this.journalCopyRepository.findOne(3L), this.journalCopyService.getCopy(3L))
    }


    @Test
    fun testGetCopies() {
        var ids = arrayListOf(3L, 7L)
        var expect = HashSet<JournalCopy>()
        expect.add(this.journalCopyRepository.findOne(3L))
        expect.add(this.journalCopyRepository.findOne(7L))
        var result = HashSet<JournalCopy>(this.journalCopyService.getCopies(ids))

        Assert.assertEquals(expect, result)
    }

    /**
     * Trigger IdOfResourceNotFoundException
     * */
    @Test(expected = IdOfResourceNotFoundException::class)
    fun testGetCopyWithException() {
        this.journalCopyService.getCopy(1L)
        Assert.assertTrue(true)

    }

    @Test
    fun testGetCopiesByFkList() {
        val journalPK = JournalPK()
        journalPK.issn = "03718471"
        journalPK.year = 2017
        journalPK.index = 1
        Assert.assertEquals(arrayListOf(this.journalCopyService.getCopies(journalPK)), arrayListOf(this.journalRepository.findOne(journalPK).copies))
    }

    @Test
    fun testDeleteCopy() {
        this.journalCopyService.deleteCopy(3L)
        Assert.assertNull(this.journalCopyRepository.findOne(3L))
    }

    @Test
    fun testDeleteCopies() {
        var ids = arrayListOf(3L, 7L)
        this.journalCopyService.deleteCopies(ids)

        Assert.assertNull(this.journalCopyRepository.findOne(3L))
        Assert.assertNull(this.journalCopyRepository.findOne(7L))
    }

    /**
     * Trigger MixedCopyTypeException
     * */
    @Test(expected = MixedCopyTypeException::class)
    fun testDeleteCopiesWithException() {
        var ids = arrayListOf(1L, 3L)
        this.journalCopyService.deleteCopies(ids)
        Assert.assertTrue(true)
    }


    @Test
    fun testUpdateCopy() {
        var copy = this.journalCopyRepository.findOne(3L)
        copy.status = CopyStatus.CHECKED_OUT
        this.journalCopyService.updateCopy(copy)
        Assert.assertEquals(CopyStatus.CHECKED_OUT, this.journalCopyRepository.findOne(3L).status)
    }

    @Test
    fun testUpdateCopies() {
        var copies = this.journalCopyRepository.findAll(arrayListOf(3L, 7L))
        copies[0].status = CopyStatus.CHECKED_OUT
        copies[1].status = CopyStatus.RESERVED
        Assert.assertEquals(CopyStatus.CHECKED_OUT, this.journalCopyRepository.findOne(3L).status)
        Assert.assertEquals(CopyStatus.RESERVED, this.journalCopyRepository.findOne(7L).status)
    }

    /**
     * Test update a book-copy with JournalCopyService
     *
     * Should change nothing
     * */
    @Test
    fun testUpdateOtherKindCopy() {
        val journalPK = JournalPK()
        journalPK.issn = "03718471"
        journalPK.year = 2017
        journalPK.index = 1

        var copy = JournalCopy()
        copy.id = 1L
        copy.journal = this.journalRepository.findOne(journalPK)
        copy.status = CopyStatus.DAMAGED

        this.journalCopyService.updateCopy(copy)

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.bookCopyRepository.findOne(1L).status)

        this.journalCopyService.updateCopies(arrayListOf(copy))

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.bookCopyRepository.findOne(1L).status)

    }
}