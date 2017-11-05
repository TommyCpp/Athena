package com.athena.exception.internal;

/**
 * Created by Tommy on 2017/11/5.
 */
public class NotCopyException extends Exception {
    public Class target;

    public NotCopyException(Class target) {
        this.target = target;
    }
}
