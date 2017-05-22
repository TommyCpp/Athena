package com.athena.model

import com.athena.model.conveter.WriterConverter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class WriterConverterTest {
    @Test
    fun testWriterConverter() {
        val converter = WriterConverter()
        val testers = arrayOf("a", "b", "c")
        Assert.assertEquals("a,b,c", converter.convertToDatabaseColumn(Arrays.asList(*testers)))
        Assert.assertEquals(Arrays.asList(*arrayOf("a", "b", "c")), converter.convertToEntityAttribute("a,b,c"))
    }
}
