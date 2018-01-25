package com.athena.util

import com.athena.model.publication.Book
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
class EntityUtilTest {

    @Test
    fun testCopyFromParent() {
//        val user = User()
//        user.id = 1L
//        user.email = "aspecttest@aspect.com"
//        user.username = "injectUser"
    }

    @Test
    fun testPartialUpdateEntity_ShouldPartialUpdateTarget() {
        val testEntity: Book = Book()
        testEntity.isbn = 972010292392L
        testEntity.title = "test Title"
        testEntity.price = 1223.2

        var params = HashMap<String, Any>()
        params["title"] = "changed Title"
        params["price"] = 1225.3
        EntityUtil.partialUpdateEntity(testEntity, params.entries)

        Assert.assertEquals("changed Title", testEntity.title)
        Assert.assertTrue((testEntity.price - 1225.3) < 0.000000000001)
    }
}