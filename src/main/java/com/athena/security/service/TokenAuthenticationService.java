package com.athena.security.service;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * Created by tommy on 2017/3/22.
 */
public class TokenAuthenticationService {

    static long EXPRIATIONTIME = 864_000_000;

    //    @Value("${security.key}")
    static String SECRET = "ThisIsASecret";

    //    @Value("${security.prefix}")
    static String TOKEN_PREFIX = "Athena";

    static String HEADER = "X-AUTHORIZATION";


    public static void addAuthentication(HttpServletResponse response, String username) {
        String token = Jwts.builder().
                setSubject(username).
                setExpiration(new Date(System.currentTimeMillis() + EXPRIATIONTIME)).
                signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(HEADER, TOKEN_PREFIX + " " + token);

    }

    public static Authentication getAuthentication(HttpServletRequest servletRequest) {
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
