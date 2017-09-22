package com.athena.service

import com.athena.model.AudioCopy
import com.athena.model.CopyStatus
import com.athena.repository.jpa.AudioCopyRepository
import com.athena.repository.jpa.AudioRepository
import com.athena.repository.jpa.BookCopyRepository
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
import javax.validation.ConstraintViolationException

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
        this.audioCopyService.addCopy(copy)
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

        this.audioCopyService.updateCopy(copy1)

        Assert.assertEquals(CopyStatus.AVAILABLE, this.audioCopyRepository.findByIdAndAudioIsNotNull(8L).status)

    }


    /**
     * Update BookCopy with AudioCopyService. Should be prevented
     * */
    @Test(expected= ConstraintViolationException::class)
    fun testModifyOtherKindCopy() {
        var book_copy = AudioCopy()
        book_copy.id = 1L
//        book_copy.audio = this.audioRepository.findOne("CNM010100300")
        book_copy.status = CopyStatus.DAMAGED


        this.audioCopyService.updateCopy(book_copy)

        Assert.assertNotEquals(CopyStatus.DAMAGED, this.bookCopyRepository.findByIdAndBookIsNotNull(1L).status)
    }
}