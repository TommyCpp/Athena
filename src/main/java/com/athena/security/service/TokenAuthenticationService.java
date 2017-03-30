package com.athena.security.service;


import com.athena.model.User;
import com.athena.repository.UserRepository;
import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private UserRepository userRepository;

    public void addAuthentication(HttpServletResponse response,Account account) {
        Long id = account.getId();
        String token = Jwts.builder().
                setSubject(id.toString()).
                setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(EXPRIATIONTIME))).
                signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(HEADER, TOKEN_PREFIX + " " + token);

    }

    public Authentication getAuthentication(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader(HEADER);
        if (token != null) {
            Long id = Long.valueOf(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject());
            User user = userRepository.findOne(id);
            Account account = new Account(user);
            return new JwtAuthenticationToken(account,true);
        }
        return null;

    }
}
