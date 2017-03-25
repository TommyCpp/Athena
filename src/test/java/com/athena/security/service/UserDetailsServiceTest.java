package com.athena.security.service;

import com.athena.model.User;
import com.athena.security.model.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    @Test
    public void testAccount(){
        User user= new User();
        user.setEmail("test");
        user.setPassword("12345");
        user.setWechatId("123235445");
        user.setIdentity("ADMIN");
        user.setId(Long.valueOf("123"));
        Account account = new Account(user);
        Assert.assertEquals(account.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")),true);

    }

}