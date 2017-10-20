package com.athena.repository.jpa.copy;

import com.athena.model.Copy;
import com.athena.util.VariableNameUtil;
import org.apache.commons.lang3.StringUtils;

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

    default List<T> isNotDeletable(Class<? extends Copy> targetClass, ID id, EntityManager em, List<Integer> deletableStatus) {
        String tableName = null;                                            // name of the target table
        List<String> publicationPk = new ArrayList<>(5);        // target class's id
        int deletableCount = deletableStatus.size();                        // how many status is deletable

        // position holder for in-clause
        String[] deletablePositionHolders = new String[deletableCount];     // ?1,?2,... to hold the position of deletableStaus
        for (int i = 0; i < deletableCount; i++) {
            deletablePositionHolders[i] = "?" + (i + 1);
        }
        String inClause = StringUtils.join(deletablePositionHolders, ",");

        // template of Native query
        String template = "SELECT * FROM {0} INNER JOIN copy ON copy.id = {0}.copy_id WHERE `status` NOT IN ({1}) AND {2}";

        // extra fields from entity
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            // getByPublications the joinTable's name
            JoinTable[] joinTables = field.getAnnotationsByType(JoinTable.class);
            if (joinTables.length == 1) {
                tableName = (joinTables[0]).name();
                //getByPublications the corresponding publication type
                Class publication = field.getType();
                for (Field publicationField : publication.getDeclaredFields()) {
                    Id[] ids = publicationField.getAnnotationsByType(Id.class);// getByPublications field that has @Id
                    if (ids.length == 1) {
                        // if has @Id
                        publicationPk.add(publicationField.getName());
                    }
                }
                break;
            }
        }

        //extra method from entity
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            JoinTable[] joinTables = method.getAnnotationsByType(JoinTable.class);
            if (joinTables.length == 1) {
                tableName = (joinTables[0]).name();
                Class publication = method.getReturnType();
                for (Method publicationMethod : publication.getDeclaredMethods()) {
                    Id[] ids = publicationMethod.getAnnotationsByType(Id.class);// getByPublications field that has @Id
                    if (ids.length == 1) {
                        // if has @Id
                        publicationPk.add(publicationMethod.getName().replace("getByPublications", ""));
                    }
                }
                break;
            }
        }


        //concat the publicationPK
        publicationPk = publicationPk.stream().map(VariableNameUtil::to_).collect(Collectors.toList()); // change the name style


        //where clause of primary key
        String whereClause = "";

        //clause for single primary key
        String pkClause = "";
        Map<String, Integer> publicationPkToIndex = new HashMap<>();
        int offset = deletableCount + 1;
        for (int i = offset; i < publicationPk.size() + offset; i++) {
            pkClause = "`" + publicationPk.get(i - offset) + "`=?" + i;
            if (i == offset) {
                whereClause += pkClause;
            } else {
                whereClause += " AND " + pkClause;
            }
            publicationPkToIndex.put(publicationPk.get(i - offset), i);
        }

        //generate query
        Query query = em.createNativeQuery(MessageFormat.format(template, tableName, inClause, whereClause));
        //set Parameter
        for (int i = 0; i < deletableCount; i++) {
            query.setParameter(i + 1, deletableStatus.get(i));
        }
        if (publicationPkToIndex.size() == 1) {
            //if the ID is single attribute.
            query.setParameter(deletableCount + 1, id);

        } else {
            //if the ID is complex object.
            for (Map.Entry<String, Integer> entry : publicationPkToIndex.entrySet()) {
                //get value
                String methodName = "get" + VariableNameUtil.toCamel(entry.getKey(), true);
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