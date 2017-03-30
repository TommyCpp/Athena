package com.athena.security.service;

import com.athena.model.User;
import com.athena.repository.UserRepository;
import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by Tommy on 2017/3/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JwtAuthenticationProviderTest {

    private JwtAuthenticationToken jwtAuthenticationToken;

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private User tester;

    @Before
    public void setup(){
        this.tester = new User();
        tester.setWechatId("test");
        tester.setUsername("test");
        tester.setIdentity("ADMIN");
        tester.setPassword("123456");
        tester.setEmail("test@test.com");
        userRepository.save(tester);
        jwtAuthenticationToken = new JwtAuthenticationToken(tester);
        jwtAuthenticationProvider = new JwtAuthenticationProvider(service,passwordEncoder);
    }

    @Test
    public void testAuthenticate(){
        User user = new User();
        user.setId(tester.getId());
        user.setPassword("123456");
        Authentication result = jwtAuthenticationProvider.authenticate(new JwtAuthenticationToken(user));
        Assert.assertTrue(result.isAuthenticated());
        Assert.assertEquals(result.getPrincipal(),new Account(this.tester));
    }

}
