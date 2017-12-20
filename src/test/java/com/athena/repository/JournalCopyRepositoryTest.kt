package com.athena.repository

import com.athena.model.JournalPK
import com.athena.repository.jpa.JournalRepository
import com.athena.repository.jpa.copy.JournalCopyRepository
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
 * Created by Tommy on 2017/10/12.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:copies.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml")
open class JournalCopyRepositoryTest {
    @Autowired
    lateinit var journalCopyRepository: JournalCopyRepository

    @Autowired
    lateinit var journalRepository: JournalRepository


    @Test
    fun testIsNotDeletable() {
        val journalPk: JournalPK = JournalPK()
        journalPk.issue = 22
        journalPk.year = 2016
        journalPk.issn = "03718473"
        var journalCopies = this.journalCopyRepository.isNotDeletable(journalPk)
        Assert.assertEquals(1,journalCopies.size)

    }
}