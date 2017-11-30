package com.athena.service

import com.athena.exception.http.ResourceNotDeletable
import com.athena.model.Book
import com.athena.model.JournalPK
import com.athena.model.Publication
import com.athena.model.Publisher
import com.athena.repository.jpa.AudioRepository
import com.athena.repository.jpa.BookRepository
import com.athena.repository.jpa.JournalRepository
import com.athena.repository.jpa.PublisherRepository
import com.athena.service.publication.PublisherService
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
import java.util.*
import javax.transaction.Transactional
import kotlin.collections.HashMap

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
        val actual = this.publisherService.get("806")
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
        journalPK.issue = 22
        publications.add(this.journalRepository.findOne(journalPK))
        publications.add(this.audioRepository.findOne("CNM010100300"))

        Assert.assertEquals(HashSet<Publisher>(result), this.publisherService.getByPublications(publications))
    }

    @Test
    fun testPartialUpdate() {
        val attributeKVs = HashMap<String, Any>()
        val id = "127"
        val bookList = arrayListOf<Book>(this.bookRepository.findOne(9785867649253L))

        attributeKVs.put("name", "ChangedPublisherName")
        attributeKVs.put("books", bookList)

        this.publisherService.update(id, attributeKVs.entries)

        val afterChange = this.publisherRepository.findOne(id)
        Assert.assertEquals(afterChange.name, attributeKVs["name"])
        Assert.assertEquals(setOf(afterChange.books), setOf(bookList))

    }

    @Test
    fun testDelete(){
        val publisherToBeDeleted = this.publisherRepository.findOne("128")
        this.publisherService.delete("128")
        Assert.assertNull(this.publisherRepository.findOne("128"))
    }

    @Test(expected = ResourceNotDeletable::class)
    fun testDeleteNotDeletablePublisher(){
        this.publisherService.delete("127")
        Assert.assertNull(this.publisherRepository.findOne("127"))
    }
}