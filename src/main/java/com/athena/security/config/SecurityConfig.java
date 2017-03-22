package com.athena.security.config;

import com.athena.security.filter.JwtAuthenticationFilter;
import com.athena.security.filter.JwtLoginFilter;
import com.athena.security.service.AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
@EnableWebSecurity
//todo:用户鉴权和管理
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.key}")
    private String secret;


    public static String JWT_TOKEN_HEADER_PARAM;
    public static String HEADER_PREFIX = "Athena "; //Jwt Header prefix

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint()).and()

                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
    }


    @Bean
    static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
}
