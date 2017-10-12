package com.athena.repository.util;

import com.athena.model.Copy;
import com.athena.util.NameUtil;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tommy on 2017/10/12.
 */
public class CopyQueryGenerator {
    public static String select(Class<? extends Copy> target) {
        String tableName = null;
        List<String> publicationPk = new ArrayList<>(5);
        String template = "SELECT * FROM {0} INNER JOIN COPY ON copy.id = {0}.copy_id WHERE `status` NOT IN (?1) AND {1}";
        Field[] fields = target.getDeclaredFields();
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
        Method[] methods = target.getDeclaredMethods();
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
        for (int i = 2; i < publicationPk.size() + 2; i++) {
            if (i == 2) {
                whereClause += publicationPk.get(i - 2) + "=?" + i;
            } else {
                whereClause += " AND " + publicationPk.get(i - 2) + "=?" + i;
            }
        }
        return MessageFormat.format(template, tableName, whereClause);
    }
}