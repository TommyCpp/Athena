package com.athena.model

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
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:copies.xml", "classpath:journals.xml")
open class JournalTest {
    @Autowired var journalRepository: JournalRepository? = null

    @Test
    fun testJournal() {
        var journalPk: JournalPK = JournalPK()
        journalPk.issn = "03718471"
        journalPk.year = 2017
        journalPk.index = 1
        Assert.assertNotNull(this.journalRepository!!.findOne(journalPk))

        journalPk.index = 2
        var journal: Journal = Journal()
        journal.id = journalPk
        journal.title = "Test"
        this.journalRepository!!.save(journal)

        Assert.assertNotNull(this.journalRepository!!.findOne(journal.id))

    }

}