package com.athena.exception;

/**
 * Created by Tommy on 2017/6/2.
 */
public class BaseException extends Exception {
    protected int statusCode;
    protected int code;
    protected String message;

    public BaseException() {
        statusCode = 500;
        code = 5000;
        message = "Some error encountered";
    }

    public String toString(){
        return "{\"code\":\"" + this.code + "\",\"message\":\"" + this.message + "\"}";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
