package com.athena.exception.internal;

/**
 * Created by Tommy on 2018/1/1.
 */
public class CannotPartialUpdateIdFieldException extends Exception {
    private Class targetClass;

    public CannotPartialUpdateIdFieldException(Class targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public String toString() {
        return "Cannot update entity's id via partial update";
    }
}
