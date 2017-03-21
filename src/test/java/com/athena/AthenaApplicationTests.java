package com.athena;

import com.athena.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AthenaApplicationTests {
    @Autowired
    private UserService service;

    @Value("${url.api}")
    private String test;

    @Test
    public void contextLoads() {

    }

    @Test
    public void environment() {
        Assert.assertEquals(test, "/api");

    }

}
