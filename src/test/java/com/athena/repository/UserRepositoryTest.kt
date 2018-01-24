package com.athena.repository

import com.athena.repository.jpa.BlockRecordRepository
import com.athena.repository.jpa.UserRepository
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
 * Created by Tommy on 2018/1/25.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:users.xml", "classpath:user_identity.xml", "classpath:blocks.xml")
open class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var blockRecordRepository: BlockRecordRepository

    @Test
    @Transactional
    open fun testGetBlockRecord_ShouldGetOne() {
        val blockedUser = this.userRepository.findOne(11L)
        Assert.assertNotEquals(0, blockedUser.blockRecords.count())
    }

    @Test
    @Transactional
    open fun testSaveBlockRecordWithUserRepository_ShouldMofityTheBlockRecord() {
        val blockedUser = this.userRepository.findOne(11L)
        Assert.assertEquals(true, blockedUser.blockRecords[0].enabled )
        blockedUser.blockRecords[0].enabled = false
        this.userRepository.save(blockedUser)
        val blockRecord = this.blockRecordRepository.findOne("ldirkchsd9JD7FCN7renbf87fdnr74ng")
        Assert.assertEquals(false, blockRecord.enabled)
    }

}