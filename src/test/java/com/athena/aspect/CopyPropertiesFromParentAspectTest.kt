package com.athena.aspect

import com.athena.model.BlockedUser
import com.athena.model.User
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/11/27.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class CopyPropertiesFromParentAspectTest {

    @Test
    fun testAspect() {
        val user = User()
        user.id = 1L
        user.email = "aspecttest@aspect.com"
        user.username = "injectUser"
        val blockedUser = BlockedUser(user)
        Assert.assertEquals(user.id, blockedUser.id)
        Assert.assertEquals(user.email, blockedUser.email)
        Assert.assertEquals(user.username, blockedUser.username)
    }
}