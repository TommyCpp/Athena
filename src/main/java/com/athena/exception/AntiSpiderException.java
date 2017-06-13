package com.athena.exception;

/**
 * Created by tommy on 2017/6/13.
 */
public class AntiSpiderException extends BaseException {
    public AntiSpiderException() {
        this.statusCode = 429;
        this.code = 4290;
        this.message = "Reject the request due to violation to anti spider rule";
    }

    public AntiSpiderException(int statusCode, int code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }
}
