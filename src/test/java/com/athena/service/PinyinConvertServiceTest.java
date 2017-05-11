package com.athena.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tommy on 2017/5/11.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PinyinConvertServiceTest {
    @Autowired
    private PinyinConvertService service;

    @Test
    public void testGetPinYin() {
        String s = "侧和";
        Assert.assertEquals("ce,he", service.getPinYin(s));
    }

    @Test
    public void testGetShortPinYin() {
        String s = "第三轮";
        Assert.assertEquals("dsl", service.getShortPinYin(s));
    }

    @Test
    public void testExceptionHandler() {
        String s = "";
        Assert.assertEquals("", service.getShortPinYin(s));
        Assert.assertEquals("", service.getPinYin(s));
    }

}
