package com.athena.security.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by Tommy on 2017/3/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JwtAuthenticationTokenProviderTest {



    @Test
    public void testPasswordEncoder(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Assert.assertEquals("123456", passwordEncoder.encode("123456"));
    }
}
