package com.athena.config;

import com.athena.service.security.JwtAuthenticationProvider;
import com.athena.service.security.TokenAuthenticationService;
import com.athena.util.filter.JwtAuthenticationFilter;
import com.athena.util.filter.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${web.url.prefix}")
    private final String URL_PREFIX = null;


    private final
    JwtAuthenticationProvider jwtAuthenticationProvider;

    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider, TokenAuthenticationService tokenAuthenticationService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()

                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(this.URL_PREFIX + "/login").permitAll()
                .antMatchers(this.URL_PREFIX + "/books/**").permitAll()
                .antMatchers(this.URL_PREFIX + "/**").authenticated()  //only authentication the APIs
                .anyRequest().permitAll()

                .and()
                .addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    private JwtLoginFilter jwtLoginFilter() throws Exception {
        return new JwtLoginFilter(this.URL_PREFIX + "/login", authenticationManager(), tokenAuthenticationService);
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenAuthenticationService);
    }


    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
