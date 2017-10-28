package com.athena.model

import com.athena.repository.jpa.AudioRepository
import com.athena.repository.jpa.copy.AudioCopyRepository
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
 * Created by Tommy on 2017/10/28.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:audios.xml", "classpath:publishers.xml", "classpath:copies.xml", "classpath:audio_copy.xml")
open class AudioCopyTest {
    @Autowired
    private lateinit var audioCopyRepository: AudioCopyRepository
    @Autowired
    private lateinit var audioRepository: AudioRepository

    @Test
    fun get() {
        val copy = this.audioCopyRepository.findOne(8L)
        Assert.assertEquals("CNM010100300", copy.audio.isrc)
    }

}