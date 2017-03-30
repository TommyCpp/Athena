package com.athena.security.service;

import com.athena.model.User;
import com.athena.repository.UserRepository;
import com.athena.security.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.mockito.BDDMockito.given;

/**
 * Created by tommy on 2017/3/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {

    @MockBean
    private AccountService accountService;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup(){
        User user = new User();
        user.setUsername("TestUser");
        user.setEmail("test@test.com");
        user.setIdentity("ADMIN");
        user.setPassword("123456");
        user.setWechatId("testtest");
        this.user = user;
        userRepository.save(user);
    }

    @Test
    public void testLoadUser(){
        given(this.accountService.loadAccountById(this.user.getId())).willReturn(new Account(this.user));
    }
}
