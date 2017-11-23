package com.athena.repository

import com.athena.repository.jpa.BlockRepository
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
 * Created by Tommy on 2017/11/23.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:blocks.xml", "classpath:users.xml")
class BlockRepositoryTest {
    @Autowired
    lateinit var blockRepository: BlockRepository
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun testFindOne() {
        val user = this.userRepository.findOne(11L)
        val result = this.blockRepository.findOne(user)
        Assert.assertEquals(10, result.handler.id)

    }
}