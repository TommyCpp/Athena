package com.athena.util.publication.search;

import com.athena.annotation.PublicationSearchParam;
import com.athena.model.publication.search.PublicationSearchVo;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tommy on 2018/2/1.
 */
public class PublicationSearchParamResolver implements HandlerMethodArgumentResolver {

    private final Integer searchCount;
    private final ObjectMapper objectMapper;

    public PublicationSearchParamResolver(Integer searchCount, ObjectMapper objectMapper) {
        this.searchCount = searchCount;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // parameter must have PublicationSearchParam annotation and it's type must be subclass or subinterface of PublicationSearchVo
        return methodParameter.getParameterAnnotation(PublicationSearchParam.class) != null && PublicationSearchVo.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // get all field name in json
        JavaType userType = objectMapper.getTypeFactory().constructType(methodParameter.getParameterType());
        BeanDescription introspection =
                objectMapper.getSerializationConfig().introspect(userType);
        List<BeanPropertyDefinition> beanPropertyDefinitionList = introspection.findProperties();
        Map<String, Boolean> properties = new HashMap<>(); // key is the field name in json and value is whether this property is array.
        for (BeanPropertyDefinition propertyDefinition : beanPropertyDefinitionList) {
            properties.put(propertyDefinition.getName(), propertyDefinition.getField().getRawType().isArray());
        }


        Set<String> keys = properties.keySet();
        Map<String, String[]> requestParameterMap = nativeWebRequest.getParameterMap();
        Map<String, Object> parameters = new HashMap<>();
        for (Map.Entry<String, String[]> entry : requestParameterMap.entrySet()) {
            if (keys.contains(entry.getKey())) {
                // if entry key is in PublicationSearchParam keys
                if (entry.getValue().length == 1 && !properties.get(entry.getKey())) {
                    // if the value only have 1 instance and its type is not array, then unwrap.
                    parameters.put(entry.getKey(), entry.getValue()[0]);
                } else {
                    // else put to result
                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (!parameters.containsKey("count")) {
            // use default value if client is not pass value
            parameters.put("count", this.searchCount);
        }
        return this.objectMapper.convertValue(parameters, methodParameter.getParameterType());
    }
}
