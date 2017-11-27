package com.athena.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;

/**
 * Created by Tommy on 2017/11/27.
 */
@Aspect
public class CopyPropertiesFromParentAspect {
    @Around("@annotation(com.athena.annotation.CopyPropertiesFromParent)")
    public Object copyPropertiesFromParent(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        assert (arguments.length == 1);//only one para
        Object parent = arguments[0];
        Class parentClass = parent.getClass();
        Object child = joinPoint.getThis();
        Class childClass = child.getClass();
        assert (parent.getClass().isAssignableFrom(child.getClass()));// parent is the parent of child
        for (Field parentField : parentClass.getFields()) {
            try {
                Field childField = childClass.getField(parentField.getName());
                parentField.setAccessible(true);
                childField.setAccessible(true);
                childField.set(child,parentField.get(parent));
                parentField.setAccessible(false);
                parentField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return joinPoint.proceed();
    }
}
