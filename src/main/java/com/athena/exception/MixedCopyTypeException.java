package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/9/9.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MixedCopyTypeException extends InvalidCopyTypeException {
    public Class exceptType;

    public MixedCopyTypeException(Class copyType) {
        this.exceptType = copyType;
        this.code = 40011;
        this.message = String.format("payload is possible a list containing different kind of copy type. The except copy type is %s", this.exceptType.getSimpleName());
    }
}
