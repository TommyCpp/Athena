package com.athena.model.conveter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Converter(autoApply = true)
public class WriterConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return StringUtils.join(strings, ",");
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null)
            return new ArrayList<String>();
        else
            return Arrays.asList(StringUtils.split(s, ","));
    }
}
