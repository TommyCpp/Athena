package com.athena.security.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by tommy on 2017/3/27.
 */
@RunWith(SpringRunner.class)
@JsonTest
@SpringBootTest
@Transactional
public class AuthenticationTest {

    @Before
    public void setup(){

    }

    @Test
    public void testLogin(){

    }
}
