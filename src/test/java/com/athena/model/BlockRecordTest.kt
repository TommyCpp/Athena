package com.athena.model

import com.athena.repository.jpa.BlockRecordRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Test
import org.junit.runner.RunWith
import org.skyscreamer.jsonassert.JSONAssert
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
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:users.xml", "classpath:user_identity.xml", "classpath:blocks.xml")
open class BlockRecordTest {

    @Autowired
    lateinit var blockRecordRepository: BlockRecordRepository

    @Test
    fun testJson() {
        val blockRecord = this.blockRecordRepository.findOne("ldirkchsd9JD7FCN7renbf87fdnr74ng")
        val jsonResult = ObjectMapper().writeValueAsString(blockRecord)

        JSONAssert.assertEquals("{\"id\":\"ldirkchsd9JD7FCN7renbf87fdnr74ng\",\"enabled\":true,\"createdAt\":null,\"note\":null,\"blockHandler\":{\"id\":10,\"username\":\"superadmin\",\"wechatId\":\"awesomeadmin\",\"email\":\"admin@athena.com\",\"identity\":[\"ROLE_SUPERADMIN\"],\"phoneNumber\":\"1526421813\",\"isBlocked\":false},\"blockedUser\":{\"id\":11,\"username\":\"test\",\"wechatId\":\"cha\",\"email\":\"sldjf@sldfj.com\",\"identity\":[\"ROLE_READER\"],\"phoneNumber\":\"1526421812\",\"isBlocked\":true},\"unblockHandler\":null}", jsonResult, false)
    }
}