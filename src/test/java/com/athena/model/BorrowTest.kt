package com.athena.model

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/11/10.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class BorrowTest {

    @Test
    fun setCopy_ShouldSetTypeAtTheSameTime() {
        val borrow = Borrow()
        val bookCopy = BookCopy()

        borrow.copy = bookCopy

        Assert.assertEquals("BookCopy", borrow.type)
    }
}