package com.athena.model

import com.athena.model.publication.Journal
import com.athena.model.publication.JournalPK
import com.athena.repository.jpa.JournalRepository
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
 * Created by Tommy on 2017/8/28.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:copies.xml", "classpath:journals.xml", "classpath:journal_copy.xml")
open class JournalTest {
    @Autowired lateinit var journalRepository: JournalRepository

    @Test
    @Transactional
    open fun testJournal() {
        var journalPk: JournalPK = JournalPK()
        journalPk.issn = "03718471"
        journalPk.year = 2017
        journalPk.issue = 1
        Assert.assertNotNull(this.journalRepository.findOne(journalPk))
        Assert.assertNotEquals(0, this.journalRepository.findOne(journalPk).copies.size) // test relationship

        journalPk.issue = 2
        var journal: Journal = Journal()
        journal.id = journalPk
        journal.title = "Test"
        this.journalRepository.save(journal)

        Assert.assertNotNull(this.journalRepository.findOne(journal.id))

    }

    @Test
    fun testUpdate() {
        val journalPk = JournalPK("03718471", 2017, 1)
        var journal = this.journalRepository.findOne(journalPk)
        journal.title = "Change Title"
        journal = this.journalRepository.save(journal)
        Assert.assertEquals("Change Title", this.journalRepository.findOne(journalPk).title)
    }

}