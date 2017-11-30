package com.athena.service

import com.athena.model.JournalPK
import com.athena.repository.jpa.JournalRepository
import com.athena.service.publication.JournalService
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

/**
 * Created by Tommy on 2017/10/26.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:journals.xml", "classpath:publishers.xml")
open class JournalServiceTest {
    @Autowired
    private lateinit var journalService: JournalService

    @Autowired
    private lateinit var journalRepository: JournalRepository

    @Test
    fun testGet() {
        val result = this.journalService.get(JournalPK("03718473", 2016, 22))
        val except = this.journalRepository.findOne(JournalPK("03718473", 2016, 22))
        Assert.assertEquals(result, except)

        val pkList = arrayListOf(JournalPK("03718473", 2016, 22), JournalPK("03718472", 2014, 23))
        val resultList = this.journalService.get(pkList)
        val exceptList = this.journalRepository.findAll(arrayListOf(JournalPK("03718473", 2016, 22), JournalPK("03718472", 2014, 23)))

        Assert.assertEquals(HashSet(resultList), HashSet(exceptList))
    }

    @Test
    fun testUpdate() {
        val pk = JournalPK("03718473", 2016, 22)
        var journal = this.journalRepository.findOne(pk)
        journal.title = "Changed Title"
        journal = this.journalService.update(journal)
        Assert.assertEquals("Changed Title", this.journalRepository.findOne(pk).title)
    }
}