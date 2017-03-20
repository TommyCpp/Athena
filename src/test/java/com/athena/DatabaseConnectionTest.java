package com.athena;

import com.athena.model.User;
import com.athena.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void createUser() {
        User user = new User();
        user.setPassword("12");
        user.setName("TEst");
        user.setIdentity("READER");
        user.setWechatId("123312");
        user.setEmail("test@test.com");
        service.save(user);
    }
}
