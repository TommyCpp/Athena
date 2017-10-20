package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Tommy on 2017/10/20.
 */
public interface ModelCRUDService<T, PK extends Serializable> {
    T get(PK pk) throws IdOfResourceNotFoundException;

    default Iterable<T> get(Iterable<PK> pks) {
        return StreamSupport.stream(pks.spliterator(), false).map(pk -> {
            try {
                return this.get(pk);
            } catch (IdOfResourceNotFoundException e) {
                e.printStackTrace();//todo: error handle
            }
            return null;
        }).collect(Collectors.toSet());
        //todo: implement in every model
    }


}
