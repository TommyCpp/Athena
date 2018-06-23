package com.athena.model.publication.search

import com.athena.model.publication.Audio
import com.athena.repository.jpa.AudioRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener

/**
 * Created by Tommy on 2018/3/22.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:audios.xml")
class AudioSearchVoTest {
    @Autowired
    lateinit var audioRepository: AudioRepository

    @Test
    fun getSpecification() {
        val audioSearchVo = AudioSearchVo()
        audioSearchVo.title = arrayOf("ForSearch")

        val result_1 = this.audioRepository.findAll(audioSearchVo.specification as Specification<Audio>)
        Assert.assertEquals(1, result_1.count())

        audioSearchVo.language = "Spanish"
        audioSearchVo.title = null

        val result_2 = this.audioRepository.findAll(audioSearchVo.specification as Specification<Audio>)
        Assert.assertEquals(1, result_2.count())


    }

}