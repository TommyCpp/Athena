package com.athena.service

import com.athena.model.JournalPK
import com.athena.model.Publication
import com.athena.model.Publisher
import com.athena.repository.jpa.AudioRepository
import com.athena.repository.jpa.BookRepository
import com.athena.repository.jpa.JournalRepository
import com.athena.repository.jpa.PublisherRepository
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
 * Created by Tommy on 2017/10/18.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml", "classpath:copies.xml", "classpath:audios.xml", "classpath:audio_copy.xml")
open class PublisherServiceTest {
    @Autowired lateinit var publisherRepository: PublisherRepository
    @Autowired lateinit var publisherService: PublisherService
    @Autowired lateinit var bookRepository: BookRepository
    @Autowired lateinit var journalRepository: JournalRepository
    @Autowired lateinit var audioRepository: AudioRepository

    @Test
    fun testGetPublisher() {
        val except = this.publisherRepository.findOne("806")
        val actual = this.publisherService.getPublisher("806")
        Assert.assertEquals(except, actual)
    }

    @Test
    fun testGetPublishers() {
        val result_id = arrayListOf("922", "127")
        val result = result_id.map { this.publisherRepository.findOne(it) }

        val publications = arrayListOf<Publication>()
        publications.add(this.bookRepository.findOne(9783158101891L))
        val journalPK = JournalPK()
        journalPK.issn = "03718473"
        journalPK.year = 2016
        journalPK.index = 22
        publications.add(this.journalRepository.findOne(journalPK))
        publications.add(this.audioRepository.findOne("CNM010100300"))

        Assert.assertEquals(HashSet<Publisher>(result), this.publisherService.getPublishers(publications))
    }
}