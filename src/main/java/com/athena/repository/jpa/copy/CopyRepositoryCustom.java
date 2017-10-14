package com.athena.repository.jpa.copy;

import com.athena.model.Copy;
import com.athena.util.NameUtil;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
public interface CopyRepositoryCustom<T extends Copy, ID> {
    void update(T copy);

    List<T> isNotDeletable(ID id);

    @Transactional
    default void update(Iterable<T> copies) {
        StreamSupport.stream(copies.spliterator(), false).forEach(this::update);
    }

    default List<T> isNotDeletable(Class<? extends Copy> targetClass, ID id, EntityManager em, String deletableStauts) {
        String tableName = null;
        List<String> publicationPk = new ArrayList<>(5);
        String template = "SELECT * FROM {0} INNER JOIN copy ON copy.id = {0}.copy_id WHERE `status` NOT IN (?1) AND {1}";
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            JoinTable[] joinTables = field.getAnnotationsByType(JoinTable.class);
            if (joinTables.length == 1) {
                tableName = (joinTables[0]).name();
                Class publication = field.getType();
                for (Field publicationField : publication.getDeclaredFields()) {
                    Id[] ids = publicationField.getAnnotationsByType(Id.class);// get field that has @Id
                    if (ids.length == 1) {
                        // if has @Id
                        publicationPk.add(publicationField.getName());
                    }
                }
                break;
            }
        }
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            JoinTable[] joinTables = method.getAnnotationsByType(JoinTable.class);
            if (joinTables.length == 1) {
                tableName = (joinTables[0]).name();
                Class publication = method.getReturnType();
                for (Method publicationMethod : publication.getDeclaredMethods()) {
                    Id[] ids = publicationMethod.getAnnotationsByType(Id.class);// get field that has @Id
                    if (ids.length == 1) {
                        // if has @Id
                        publicationPk.add(publicationMethod.getName().replace("get", ""));
                    }
                }
                break;
            }
        }
        //concat the publicationPK
        publicationPk = publicationPk.stream().map(NameUtil::to_).collect(Collectors.toList()); // change the name style
        String whereClause = "";
        String pkClause = "";
        Map<String, Integer> publicationPkToIndex = new HashMap<>();
        for (int i = 2; i < publicationPk.size() + 2; i++) {
            pkClause = "`" + publicationPk.get(i - 2) + "`=?" + i;
            if (i == 2) {
                whereClause += pkClause;
            } else {
                whereClause += " AND " + pkClause;
            }
            publicationPkToIndex.put(publicationPk.get(i - 2), i);
            //todo: solve the keyword problem
        }
        Query query = em.createNativeQuery(MessageFormat.format(template, tableName, whereClause));
        //set Parameter
        query.setParameter(1, deletableStauts);
        if (publicationPkToIndex.size() == 1) {
            //if the ID is single attribute.
            query.setParameter(2, id);

        } else {
            //if the ID is complex object.
            for (Map.Entry<String, Integer> entry : publicationPkToIndex.entrySet()) {
                //get value
                String methodName = "get" + NameUtil.toCamel(entry.getKey(), true);
                try {
                    Object attributeValue = id.getClass().getMethod(methodName).invoke(id);
                    //set value
                    query.setParameter(entry.getValue(), attributeValue);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return query.getResultList();
    }

}