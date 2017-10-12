package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/10/12.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceNotDeletable extends BaseException {
    public Class notDeletableType;

    public ResourceNotDeletable(Class notDeletableType) {
        this.notDeletableType = notDeletableType;
        this.code = 403;
        this.code = 4031;
        this.message = "cannot delete certain resource because some other resource is not deletable now";
    }
}
