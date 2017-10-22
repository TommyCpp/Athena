package com.athena.exceptionhandler;

import com.athena.exception.internal.EntityAttributeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.MessageFormat;

/**
 * Created by Tommy on 2017/10/23.
 */
@ControllerAdvice(basePackages = "com.athena.controller")
public class EntityAttributeNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handle(EntityAttributeNotFoundException exception){
        String template = "{0} class does not contain attribute name {1}";
        return ResponseEntity.badRequest().body(MessageFormat.format(template, exception.getTargetClass().getSimpleName(), exception.getAttributeName()));
    }

}
