package com.athena.model.conveter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
public class WriterConverter implements AttributeConverter<List<String>,String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return StringUtils.join(strings, ",");
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.asList(StringUtils.split(s, ","));
    }
}
