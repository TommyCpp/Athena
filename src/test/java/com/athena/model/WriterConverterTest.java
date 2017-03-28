package com.athena.model;

import com.athena.model.conveter.WriterConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * Created by tommy on 2017/3/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WriterConverterTest {
    @Test
    public void testWriterConverter(){
        WriterConverter converter = new WriterConverter();
        String[] testers = {
                "a","b","c"
        };
        Assert.assertEquals("a,b,c", converter.convertToDatabaseColumn(Arrays.asList(testers)));
        Assert.assertEquals(Arrays.asList(new String[]{"a", "b", "c"}), converter.convertToEntityAttribute("a,b,c"));
    }
}
