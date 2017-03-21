package com.athena.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by tommy on 2017/3/20.
 */
@Configuration
@EnableWebSecurity
//todo:用户鉴权和管理
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static String JWT_TOKEN_HEADER_PARAM;
    public static String HEADER_PREFIX = "Athena "; //Jwt Header prefix

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
