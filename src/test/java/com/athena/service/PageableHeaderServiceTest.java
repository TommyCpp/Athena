package com.athena.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tommy on 2017/5/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageableHeaderServiceTest {
    @Autowired
    private PageableHeaderService service;

    @Test
    public void testGetQueryMap() {
        String query = "author=test,test&last_cursor=4&count=20";
        service.getQueryMap(query);
//TODO:Continue
    }
}
