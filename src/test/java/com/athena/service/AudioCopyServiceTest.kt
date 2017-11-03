package com.athena.service

import com.athena.model.AudioCopy
import com.athena.model.CopyStatus
import com.athena.repository.jpa.AudioRepository
import com.athena.repository.jpa.copy.AudioCopyRepository
import com.athena.repository.jpa.copy.BookCopyRepository
import com.athena.service.copy.AudioCopyService
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
 * Created by Tommy on 2017/9/15.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml", "classpath:book_copy.xml", "classpath:journal_copy.xml", "classpath:journals.xml", "classpath:copies.xml", "classpath:audios.xml", "classpath:audio_copy.xml")
open class AudioCopyServiceTest {
    @Autowired private lateinit var audioCopyService: AudioCopyService
    @Autowired private lateinit var audioRepository: AudioRepository
    @Autowired private lateinit var audioCopyRepository: AudioCopyRepository
    @Autowired private lateinit var bookCopyRepository: BookCopyRepository


    @Test
    fun testAddCopy() {
        var copy = AudioCopy()
        copy.audio = this.audioRepository.findOne("CNM010100300")
        copy.status = CopyStatus.BOOKED
        this.audioCopyService.add(copy)
        Assert.assertNotNull(audioCopyRepository.findOne(copy.id))
    }


    @Test
    fun testDeleteCopyByIsrc() {
        val audio = this.audioRepository.findOne("CNM010100300")
        this.audioCopyService.deleteCopies("CNM010100300")

        Assert.assertEquals(0, this.audioCopyRepository.findByAudio(audio).size)
    }

    @Test
    fun testUpdateAudioCopy() {
        var copy1 = this.audioCopyRepository.findByIdAndAudioIsNotNull(8L)
        copy1.status = CopyStatus.AVAILABLE

        this.audioCopyService.update(copy1)

        Assert.assertEquals(CopyStatus.AVAILABLE, this.audioCopyRepository.findByIdAndAudioIsNotNull(8L).status)

    }


    /**
     * Test update a book-copy with AudioCopyService
     *
     * Should change nothing
     * */
    @Test
    fun testUpdateOtherKindCopy() {

        var copy = AudioCopy()
        copy.id = 1L
        copy.audio = this.audioRepository.findOne("CNM010100300")
        copy.status = CopyStatus.DAMAGED

        this.audioCopyService.update(copy)

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.bookCopyRepository.findOne(1L).status)

        this.audioCopyService.update(arrayListOf(copy))

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.bookCopyRepository.findOne(1L).status)

    }
}