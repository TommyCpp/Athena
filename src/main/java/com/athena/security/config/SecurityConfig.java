package com.athena.security.config;

import com.athena.security.filter.JwtAuthenticationFilter;
import com.athena.security.filter.JwtLoginFilter;
import com.athena.security.service.JwtAuthenticationProvider;
import com.athena.security.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by tommy on 2017/3/20.
 */
@Configuration
@ComponentScan
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public static String JWT_TOKEN_HEADER_PARAM;
    public static String HEADER_PREFIX;//Jwt Header prefix


    @Autowired
    private
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()

                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/v1/books").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    private JwtLoginFilter jwtLoginFilter() throws Exception {
        return new JwtLoginFilter("/login", authenticationManager(), tokenAuthenticationService);
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenAuthenticationService);
    }


    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
