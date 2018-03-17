package com.athena.service.publication;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.publication.Publication;
import com.athena.service.ModelCRUDService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/10/1.
 */
public interface PublicationService<T extends Publication, K extends Serializable> extends ModelCRUDService<T, K> {

    List<T> get(Iterable<K> ks);

    @Transactional
    default List<T> update(Iterable<T> ts) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        List<T> result = new ArrayList<>();
        for (T t : ts) {
            result.add(this.update(t));
        }
        return result;
    }

    List<T> add(Iterable<T> ts);

    Page<T> search(Specification<T> specification, Pageable pageable);
}
