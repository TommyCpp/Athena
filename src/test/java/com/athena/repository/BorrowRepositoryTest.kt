package com.athena.repository

import com.athena.model.AudioCopy
import com.athena.repository.jpa.BorrowRepository
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

/**
 * Created by Tommy on 2017/11/9.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:copies.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml", "classpath:audios.xml", "classpath:audio_copy.xml", "classpath:borrows.xml", "classpath:users.xml")
open class BorrowRepositoryTest {

    @Autowired
    lateinit var borrowRepository: BorrowRepository

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Test
    fun testGetPublication() {
        val borrow = this.borrowRepository.findOne("21038468-9cff-4b6e-b2a4-8e4843005716")
        val publisher = this.publisherRepository.findOne("127")

        Assert.assertEquals(publisher, this.borrowRepository.getPublication(borrow).publisher)
    }

    @Test
    fun testFindFirstByCopyAndEnableIsFalseOrderByUpdatedDateDesc() {
        val copy = AudioCopy()
        copy.id = 11
        val borrow = this.borrowRepository.findFirstByCopyAndEnableIsFalseOrderByUpdatedDateDesc(copy)
        Assert.assertEquals("825a790d-52a1-4231-9781-b1e7713dae1d", borrow.id)
    }

}