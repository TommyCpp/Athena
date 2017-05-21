package com.athena.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/5/22.
 */
@SpringBootTest
@RunWith(SpringRunner::class)
open class PinyinConvertServiceTest {
    @Autowired
    lateinit var service: PinyinConvertService

    @Test
    fun testGetPinyin() {
        val s = "侧和"
        Assert.assertEquals("ce,he", service.getPinYin(s))
    }

    @Test
    fun testGetShortPinyin() {
        val s = "第三轮"
        Assert.assertEquals("dsl", service.getShortPinYin(s))
    }

    @Test
    fun testExceptionHandler() {
        val s = ""
        Assert.assertEquals("", service.getShortPinYin(s))
        Assert.assertEquals("", service.getPinYin(s))
    }
}
