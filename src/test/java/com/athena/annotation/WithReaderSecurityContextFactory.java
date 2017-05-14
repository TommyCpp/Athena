package com.athena.annotation;

import com.athena.model.User;
import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * Created by Tommy on 2017/5/14.
 */
public class WithReaderSecurityContextFactory implements WithSecurityContextFactory<WithReader> {

    @Override
    public SecurityContext createSecurityContext(WithReader withReader) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setIdentity("READER");
        user.setUsername(withReader.username());
        user.setPassword(encoder.encode(withReader.password()));
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setWechatId("testWechat");
        user.setPhoneNumber("11111111111");
        Account principal = new Account(user);

        Authentication authentication = new JwtAuthenticationToken(principal, true);

        context.setAuthentication(authentication);
        return context;
    }
}
