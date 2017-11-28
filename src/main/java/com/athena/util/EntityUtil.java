package com.athena.util;

import com.athena.exception.http.ResourceNotFoundByIdException;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by Tommy on 2017/11/28.
 */
public class EntityUtil {
    public static void copyFromParent(Object child, Object parent) {
        Class parentClass = parent.getClass();
        Class childClass = child.getClass();
        assert (parent.getClass().isAssignableFrom(childClass));// parent is the parent of child
        for (Field field : parentClass.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                field.set(child,field.get(parent));
                field.setAccessible(false);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void requireEntityNotNull(Object object) throws ResourceNotFoundByIdException {
        try{
            Objects.requireNonNull(object);
        }catch (NullPointerException e){
            throw new ResourceNotFoundByIdException();
        }
    }
}
