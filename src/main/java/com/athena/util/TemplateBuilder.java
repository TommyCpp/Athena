package com.athena.util;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Tommy on 2017/12/24.
 */
@Component
public class TemplateBuilder {

    public String build(String template, Map<String, String> templateParas) {
            return StrSubstitutor.replace(template, templateParas);
    }
}
