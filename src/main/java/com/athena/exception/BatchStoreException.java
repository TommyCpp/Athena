package com.athena.exception;

import java.util.List;

/**
 * Created by Tommy on 2017/8/27.
 */
public class BatchStoreException extends DataException {
    public List<?> elements;
    public String type;

    public BatchStoreException(List<?> elements, String type) {
        this.elements = elements;
        this.type = type;
    }
}
