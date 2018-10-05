package com.athena.service.security

import com.athena.model.security.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils
import javax.transaction.Transactional

/**
 * Created by zhong on 2018/10/4.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
open class PrivilegeServiceTest {
    var privilegeService: PrivilegeService = PrivilegeService("SUPERADMIN,ADMIN,READER")

    private val modifiedUser = User()
    private val modifyingUser = User()

    @Before
    fun setup() {
        modifiedUser.username = "ModifiedUser"
        modifiedUser.email = "modified@test.com"
        modifiedUser.setIdentity("ROLE_ADMIN")
        modifiedUser.password = "123456"
        modifiedUser.wechatId = "testtest"

        modifyingUser.username = "ModifyingUser"
        modifyingUser.email = "modifying@test.com"
        modifyingUser.setIdentity("ROLE_SUPERADMIN")
    }

    @Test
    fun testPrivilege() {
        val res_1  = privilegeService.isCurrentUserCanOperateOn(modifiedUser, modifyingUser)
        Assert.assertTrue(res_1)

        val anotherModifyingUser = User()
        anotherModifyingUser.setIdentity("ROLE_ADMIN")
        anotherModifyingUser.username = "ModifyingUser"
        anotherModifyingUser.email = "modifying@test.com"

        val res_2 = privilegeService.isCurrentUserCanOperateOn(modifiedUser, anotherModifyingUser)
        Assert.assertFalse(res_2)
    }

}