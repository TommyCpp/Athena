package com.athena.model.domain.message

import com.athena.model.message.AuthorityUserGroup
import com.athena.repository.jpa.UserRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener

/**
 * Created by Tommy on 2017/12/24.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:users.xml", "classpath:user_identity.xml")
class AuthorityUserGroupTest {
    @Autowired
    lateinit var repository: UserRepository

    @Test
    fun testFetchUsers_ShouldReturnAllUserHasADMINIdentity() {
        val authorityUserGroup = AuthorityUserGroup(SimpleGrantedAuthority("ROLE_ADMIN"))
        val results = authorityUserGroup.fetchUsers(repository)
        Assert.assertNotEquals(0, results.size)
        for (result in results) {
            Assert.assertTrue(result.identity.indexOf("ROLE_ADMIN") != -1)
        }
    }
}