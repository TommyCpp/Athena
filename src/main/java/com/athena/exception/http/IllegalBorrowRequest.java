package com.athena.exception.http;

import com.athena.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/11/10.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalBorrowRequest extends BaseException {
    public IllegalBorrowRequest() {
        this.statusCode = 4003;
        this.code = 400;
        this.message = "Borrow request is illegal because of the user may reach the borrow limit";
    }
}
