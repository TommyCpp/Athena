package com.athena.exception.internal;

import com.athena.exception.BaseException;

/**
 * Created by Tommy on 2017/6/2.
 */
public class JwtAuthException extends BaseException {
    public JwtAuthException(Exception e) {
        super();
        this.statusCode = 401;
        this.code = 4010;
        this.message = "JWT authentication error";
        switch (e.getClass().getSimpleName()) {
            case "ExpiredJwtException":{
                this.code = 4011;
                this.message = "JWT token is expired";
            };break;
            case "UnsupportedJwtException":{
                this.code = 4012;
                this.message = "JWT token's algorithm is not supported";
            }break;
            case "MalformedJwtException ":{
                this.code = 4013;
                this.message = "Malformed JWT token";
            }break;
            case "SignatureException":{
                this.code = 4014;
                this.message = "JWT token's signature is not supported";
            }break;
        }
    }

}
