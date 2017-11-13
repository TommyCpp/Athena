package com.athena.exception.http;

import com.athena.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/11/13.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalReturnRequest extends BaseException {
    public IllegalReturnRequest() {
        this.statusCode = 400;
        this.code = 4004;
        this.message = "Cannot return copy because some error regarding the copy";
    }
}
