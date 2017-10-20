package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Tommy on 2017/10/20.
 */
public interface ModelCRUDService<T, PK extends Serializable> {
    void add(T t);

    default void add(Iterable<T> ts) {
        StreamSupport.stream(ts.spliterator(), false).forEach(this::add);
    }


    T get(PK pk) throws IdOfResourceNotFoundException;

    default Iterable<T> get(Iterable<PK> pks) {
        return StreamSupport.stream(pks.spliterator(), false).map(pk -> {
            try {
                return this.get(pk);
            } catch (IdOfResourceNotFoundException e) {
                e.printStackTrace();//todo: error handle
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
        //todo: implement in every model
    }

    T update(T t) throws IdOfResourceNotFoundException;

    @Transactional
    default Iterable<T> update(Iterable<T> ts) throws IdOfResourceNotFoundException {
        List<T> result = new ArrayList<>();
        for (T t : ts) {
            result.add(this.update(t));
        }
        return result;
    }


}
