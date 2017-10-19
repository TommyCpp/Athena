package com.athena.exception.http;

import com.athena.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 吴钟扬 on 2017/9/8.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalEntityAttributeException extends BaseException {
    public IllegalEntityAttributeException() {
        this.code = 4002;
        this.statusCode = 400;
        this.message = "some attribute in entity is illegal";
    }
}
