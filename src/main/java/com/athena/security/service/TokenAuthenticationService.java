package com.athena.security.service;


import com.athena.model.User;
import com.athena.repository.jpa.UserRepository;
import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by tommy on 2017/3/22.
 */
@Service
public class TokenAuthenticationService {

    @Value("${security.token.expirationtime}")
    private String EXPRIATIONTIME;

    @Value("${security.token.key}")
    private String SECRET;

    @Value("${security.token.prefix}")
    private String TOKEN_PREFIX;

    @Value("${security.token.header}")
    private String HEADER;

    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    @Autowired
    public TokenAuthenticationService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Add authentication in header and user body for login request.
     *
     * @param response the response
     * @param account  the account
     */
    public void addAuthentication(HttpServletResponse response, Account account) {
        Long id = account.getId();
        String token = Jwts.builder().
                setSubject(id.toString()).
                setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(EXPRIATIONTIME))).
                signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(HEADER, TOKEN_PREFIX + " " + token);
        try {
            String responseBody = objectMapper.writeValueAsString(account.getUser());
            response.getWriter().write(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();//fixme:test and error handle
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Gets authentication.
     *
     * @param servletRequest the servlet request
     * @return the authentication
     */
    public Authentication getAuthentication(HttpServletRequest servletRequest) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        String token = servletRequest.getHeader(HEADER);
        if (token != null) {
            Long id = null;
            try {
                id = Long.valueOf(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject());
            } catch (IllegalArgumentException e) {
            }
            User user = userRepository.findOne(id);
            Account account = new Account(user);
            return new JwtAuthenticationToken(account, true);
        }
        return null;

    }
}
