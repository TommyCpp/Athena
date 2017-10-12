package com.athena.repository.util

import com.athena.model.BookCopy
import com.athena.model.JournalCopy
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/10/12.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
open class CopyQueryGeneratorTest {
    @Test
    fun testSelect() {
        System.out.println(CopyQueryGenerator.select(BookCopy::class.java))
        System.out.println(CopyQueryGenerator.select(JournalCopy::class.java))
    }
}