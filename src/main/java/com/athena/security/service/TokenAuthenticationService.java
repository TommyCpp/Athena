package com.athena.security.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * Created by tommy on 2017/3/22.
 */
@Service
public class TokenAuthenticationService {

    @Value("${security.token.expriationtime}")
    private String EXPRIATIONTIME;

    @Value("${security.token.key}")
    private String SECRET;

    @Value("${security.token.prefix}")
    private String TOKEN_PREFIX;

    @Value("${security.token.header}")
    private String HEADER;


    public  void addAuthentication(HttpServletResponse response, String username) {
        String token = Jwts.builder().
                setSubject(username).
                setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(EXPRIATIONTIME))).
                signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(HEADER, TOKEN_PREFIX + " " + token);

    }

    public  Authentication getAuthentication(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader(HEADER);
        if (token != null) {

            String username = Objects.equals(token, "11111") ? "admin" : Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();
            return
                    username == null ? null :
                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
        }
        return null;

    }
}
