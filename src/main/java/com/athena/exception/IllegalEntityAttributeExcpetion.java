package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 吴钟扬 on 2017/9/8.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalEntityAttributeExcpetion extends BaseException{
    public IllegalEntityAttributeExcpetion() {
        this.code = 4002;
        this.statusCode = 400;
        this.message = "some attribute in entity is illegal";
    }
}
