package com.athena.util;

import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.exception.http.ResourceNotFoundException;
import com.athena.exception.internal.EntityAttributeNotFoundException;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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
                field.set(child, field.get(parent));
                field.setAccessible(false);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void requireEntityNotNull(Object object) throws ResourceNotFoundByIdException {
        try {
            Objects.requireNonNull(object);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundByIdException();
        }
    }

    /**
     * Partial update entity.
     * if the attribute key-value set has attribute annotated by @Id, then ignore.
     *
     * @param entity       the entity
     * @param attributeKVs the attribute k vs
     * @throws ResourceNotFoundException        the resource not found exception
     * @throws EntityAttributeNotFoundException the entity attribute not found exception
     */
    public static void partialUpdateEntity(Object entity, Iterable<Map.Entry<String, Object>> attributeKVs) throws ResourceNotFoundException, EntityAttributeNotFoundException {//todo:test
        List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
        List<Method> methods = Arrays.asList(entity.getClass().getDeclaredMethods());
        List<String> methodsNames = methods.stream().map(Method::getName).collect(Collectors.toList());
        List<String> fieldNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        Optional<Field> ifHasIdField = fields.stream().filter(field -> field.getAnnotationsByType(Id.class).length == 0).findFirst();
        if (ifHasIdField.isPresent()) {
            String idFieldName = ifHasIdField.get().getName();
            for (Map.Entry<String, Object> attributeKV : attributeKVs) {
                if (!attributeKV.getKey().equals(idFieldName)) {
                    // if the attribute key is not annotated by @Id
                    int indexOfAttribute = fieldNames.indexOf(attributeKV.getKey());
                    if (indexOfAttribute == -1) {
                        // if the name is not attribute
                        throw new EntityAttributeNotFoundException(entity.getClass(), attributeKV.getKey());
                    } else {
                        // if the name is attribute
                        try {
                            int indexOfMethod = methodsNames.indexOf("set" + VariableNameUtil.toCamel(attributeKV.getKey(), true));
                            if (indexOfMethod == -1) {
                                throw new NoSuchMethodException();
                            }
                            Method method = methods.get(indexOfMethod);
                            method.invoke(entity, attributeKV.getValue());
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            throw new EntityAttributeNotFoundException(entity.getClass(), attributeKV.getKey());
                        }
                    }
                }
            }
        } else {
            throw new ResourceNotFoundException();
        }


    }
}
