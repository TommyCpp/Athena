package com.athena.util;

import java.lang.reflect.Field;

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
}
