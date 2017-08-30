package com.athena;

import com.athena.model.User;
import com.athena.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by tommy on 2017/3/20.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class DatabaseConnectionTest {

    @Autowired
    private UserService service;

    @Autowired
    private StringRedisTemplate template;

    @Test
    public void createUser() {
        User user = new User();
        user.setPassword("12");
        user.setUsername("TEst");
        user.setIdentity("ROLE_READER");
        user.setWechatId("123312");
        user.setEmail("test@test.com");
        service.save(user);
    }

    @Test
    public void getUser(){
        service.getUser(1);
    }

    @Test
    public void testRedisConnection(){
        template.opsForValue().set("test","111");
        Assert.assertEquals("111", template.opsForValue().get("test"));
    }
}
