package com.athena.exception.internal;

import com.athena.exception.BaseException;

/**
 * Created by Tommy on 2017/8/27.
 */
public class DataException extends BaseException {
    public DataException() {
        super();
        this.statusCode = 500;
        this.code = 5001;
        this.message = "Exception regarding repository or database connection";
    }
}
