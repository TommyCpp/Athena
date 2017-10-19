package com.athena.exception.internal;

/**
 * Created by Tommy on 2017/10/19.
 */
public class EntityAttributeNotFoundException extends Exception {
    private Class targetClass;
    private String attributeName;

    public EntityAttributeNotFoundException(Class targetClass, String attributeName) {
        this.targetClass = targetClass;
        this.attributeName = attributeName;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
