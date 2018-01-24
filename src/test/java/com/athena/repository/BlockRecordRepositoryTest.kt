package com.athena.repository

import com.athena.model.BlockRecord
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

/**
 * Created by Tommy on 2018/1/25.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:users.xml", "classpath:user_identity.xml", "classpath:blocks.xml")
class BlockRecordRepositoryTest {
    @Autowired
    lateinit var blockRecordRepository: BlockRecordRepository


    @Autowired
    lateinit var userRepsitory: UserRepository

    @Test
    fun testSaveByBlockRecordRepository() {
        var blockRecord = BlockRecord()
        blockRecord.blockedUser = this.userRepsitory.findOne(11L)
        blockRecord.blockHandler = this.userRepsitory.findOne(10L)

        blockRecord = this.blockRecordRepository.save(blockRecord)
        Assert.assertEquals(10, blockRecord.blockHandler.id)
        Assert.assertEquals(11, blockRecord.blockedUser.id)

    }
}