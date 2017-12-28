package com.athena.util

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/12/28.
 *
 */
@SpringBootTest
@RunWith(SpringRunner::class)
class TemplateBuilderTest {
    @Autowired
    lateinit var templateBuilder: TemplateBuilder

    @Test
    fun testBuild_ShouldReturnTheWholeString() {
        val template = """The publication ${'$'}{name} is damage by ${'$'}{someone}"""
        val paras = HashMap<String, String>()
        paras["name"] = "test"
        paras["someone"] = "Writer"
        Assert.assertEquals("The publication test is damage by Writer", this.templateBuilder.build(template, paras))

    }
}