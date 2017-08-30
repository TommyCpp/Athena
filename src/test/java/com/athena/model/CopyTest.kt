package com.athena.model

import com.athena.repository.jpa.BookCopyRepository
import com.athena.repository.jpa.BookRepository
import com.athena.repository.jpa.CopyRepository
import com.athena.repository.jpa.JournalCopyRepository
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
 * Created by Tommy on 2017/6/9.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:copies.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml")
open class CopyTest {
    @Autowired var copyRepository: CopyRepository? = null
    @Autowired var bookCopyRepository: BookCopyRepository? = null
    @Autowired var bookRepository: BookRepository? = null
    @Autowired var journalCopyRepository: JournalCopyRepository? = null

    @Test fun testBookCopy() {
        val result = bookCopyRepository!!.findOne(1L)
        val except = "C++程序设计指南"
        Assert.assertEquals(except, result.book.title)
    }

    @Test fun testJournalCopy() {
        val result = journalCopyRepository!!.findOne(3L)
        val except = "Test Magazine"
        Assert.assertEquals(except, result.journal.title)
    }

    @Test fun testSaveBookCopy() {
        val bookCopy = BookCopy()
        bookCopyRepository!!.save(bookCopy)
        Assert.assertNotNull(bookCopyRepository!!.findOne(bookCopy.id))
    }
}
