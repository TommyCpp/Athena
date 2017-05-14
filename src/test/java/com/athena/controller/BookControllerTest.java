package com.athena.controller;

import com.athena.annotation.WithReader;
import com.athena.model.User;
import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tommy on 2017/5/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
})
@DatabaseSetup({"classpath:books.xml", "classpath:publishers.xml", "classpath:users.xml"})
@WebAppConfiguration
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

    }

    @Test
    public void testBookSearch() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setIdentity("READER");
        user.setUsername("reader");
        user.setPassword(encoder.encode("123456"));
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setWechatId("testWechat");
        user.setPhoneNumber("11111111111");
        Account principal = new Account(user);

        Authentication authentication = new JwtAuthenticationToken(principal, true);

        securityContext.setAuthentication(authentication);
        mvc.perform(get("/api/v1/books?title=elit").with(securityContext(securityContext))).andDo(print());
//        TODO:solve problem
    }
}
