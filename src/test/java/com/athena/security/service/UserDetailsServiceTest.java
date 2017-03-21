package com.athena.security.service;

import com.athena.security.model.JwtUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by tommy on 2017/3/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDetailsServiceTest {

    @Autowired
    UserDetailsServiceImpl service;


    @Test
    public void ifUserDetailsServiceCanGetUser() {

        UserDetails user = service.loadUserByUsername("1");
        Assert.assertEquals(user.getUsername(), "test");
    }

}