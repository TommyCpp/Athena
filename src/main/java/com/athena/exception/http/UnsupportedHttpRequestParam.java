package com.athena.exception.http;

import com.athena.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2018/8/10.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnsupportedHttpRequestParam extends BaseException {
    public String[] param;

    public UnsupportedHttpRequestParam(String[] param) {
        this.statusCode = 400;
        this.code = 4005;
        this.param = param;
        this.message = "Some params of http request cannot be processed by server. Params: " + String.join(",", param);
    }
}
