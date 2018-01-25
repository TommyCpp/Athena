package com.athena.util;

import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.exception.http.ResourceNotFoundException;
import com.athena.exception.internal.CannotPartialUpdateIdFieldException;
import com.athena.exception.internal.EntityAttributeNotFoundException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
    public static void partialUpdateEntity(Object entity, Iterable<Map.Entry<String, Object>> attributeKVs) throws ResourceNotFoundException, EntityAttributeNotFoundException, CannotPartialUpdateIdFieldException {
        //get all superclass of entity with annotation of MappedClass or Entity
        List<Class> entityClasses = new ArrayList<>(3);
        entityClasses.add(entity.getClass());
        Class superClass = entity.getClass().getSuperclass();
        while (superClass.getAnnotation(Entity.class) != null || superClass.getAnnotation(MappedSuperclass.class) != null) {
            entityClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }
        // get fields,including private and inherited
        List<Field> fields = new ArrayList<>();
        for (Class sc : entityClasses) {
            fields.addAll(Arrays.asList(sc.getDeclaredFields()));
        }
        List<String> fieldNames = fields.stream().map(Field::getName).collect(Collectors.toList());

        //get all methods and methods names
        List<Method> methods = Arrays.asList(entity.getClass().getMethods());
        List<String> methodsNames = methods.stream().map(Method::getName).collect(Collectors.toList());

        //get id field (which has @Id or its getter has @Id)
        Optional<Field> hasIdField = fields.stream().filter(field -> field.getAnnotationsByType(Id.class).length != 0).findFirst();
        Optional<Method> hasIdMethod = methods.stream().filter(method -> method.getDeclaredAnnotation(Id.class) != null).findFirst();
        String idFieldName = null;
        if (hasIdField.isPresent()) {
            idFieldName = hasIdField.get().getName();
        }
        if (hasIdMethod.isPresent()) {
            idFieldName = getFieldByGetter(hasIdMethod.get().getName());
        }
        // if id field cannot find, then d
        if (idFieldName == null) {
            throw new ResourceNotFoundException();
        }

        //for each one of attributes
        for (Map.Entry<String, Object> attributeKV : attributeKVs) {
            // if the attribute key is annotated by @Id
            if (attributeKV.getKey().equals(idFieldName)) {
                throw new CannotPartialUpdateIdFieldException(entity.getClass());
            } else {
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

    }


    private static String getFieldByGetter(String methodName) {
        methodName = methodName.replace("get", "");
        char[] chars = methodName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
