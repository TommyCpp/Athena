package com.athena.service

import com.athena.model.Audio
import com.athena.repository.jpa.AudioRepository
import com.athena.repository.jpa.PublisherRepository
import com.athena.repository.jpa.copy.AudioCopyRepository
import com.athena.service.publication.AudioService
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
import java.sql.Date

/**
 * Created by Tommy on 2017/10/30.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:audios.xml", "classpath:publishers.xml")
class AudioServiceTest {
    @Autowired private lateinit var audioRepository: AudioRepository
    @Autowired private lateinit var audioCopyRepository: AudioCopyRepository
    @Autowired private lateinit var audioService: AudioService
    @Autowired private lateinit var publisherRepository: PublisherRepository


    @Test
    fun testGet() {
        val expect = this.audioRepository.findOne("CNM010100300")
        val actual_1 = this.audioService.get("CNM010100300")
        Assert.assertEquals(expect, actual_1)

        val actual_2 = this.audioService.get(arrayListOf("CNM010100300"))
        Assert.assertEquals(1, actual_2.size)
        Assert.assertEquals(expect, actual_2[0])

    }


    @Test
    fun testAdd() {
        var audio = Audio()
        audio.isrc = "CNM010199999"
        audio.author = "test,test"
        audio.title = "test"
        audio.publisher = publisherRepository.findOne("128")
        audio.price = 11.1
        audio.publishDate = Date(3156136L)

        audio = this.audioService.add(audio)
        Assert.assertEquals(this.audioRepository.findOne("CNM010199999").isrc, audio.isrc)

    }


    @Test
    fun testDelete() {
        this.audioService.delete(this.audioRepository.findOne("CNM010100302"))
        Assert.assertFalse(this.audioRepository.exists("CNM010100302"))
    }

    @Test
    fun update() {
        var audio = this.audioRepository.findOne("CNM010100300")
        audio.title = "dkljas"
        audio = this.audioService.update(audio)
        Assert.assertEquals("dkljas", this.audioRepository.findOne("CNM010100300").title)
    }
}