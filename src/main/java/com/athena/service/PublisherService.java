package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.internal.EntityAttributeNotFoundException;
import com.athena.model.Publication;
import com.athena.model.Publisher;
import com.athena.repository.jpa.PublisherRepository;
import com.athena.util.VariableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by tommy on 2017/3/28.
 */
@Service
public class PublisherService implements ModelCRUDService<Publisher,String>{

    private PublisherRepository repository;
    private BookService bookService;


    /**
     * Instantiates a new Publisher service.
     *
     * @param repository  the repository
     * @param bookService the book service
     */
    @Autowired
    public PublisherService(PublisherRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }


    /**
     * Gets publisher set from publications
     *
     * @param publications the publications
     * @return the publishers
     */
    public Set<Publisher> getByPublications(Iterable<Publication> publications) {
        return StreamSupport.stream(publications.spliterator(), true).map(Publication::getPublisher).collect(Collectors.toSet());
    }

    /**
     * Gets publisher by id
     *
     * @param id the id
     * @return the publisher
     * @throws IdOfResourceNotFoundException the id of resource not found exception
     */
    public Publisher get(String id) throws IdOfResourceNotFoundException {
        Publisher publisher = this.repository.findOne(id);
        if (publisher == null) {
            throw new IdOfResourceNotFoundException();
        } else {
            return publisher;
        }
    }


    /**
     * Delete.
     *
     * @param id the id
     * @throws IdOfResourceNotFoundException the id of resource not found exception
     * @throws ResourceNotDeletable          the resource not deletable
     */
    @Transactional
    public void delete(String id) throws IdOfResourceNotFoundException, ResourceNotDeletable {
        Publisher publisher = this.repository.findOne(id);
        if (publisher == null) {
            throw new IdOfResourceNotFoundException();
        }
        //check if the book is clear and delete
        this.bookService.delete(publisher.getBooks());
        this.repository.delete(publisher);
    }

    /**
     * Update.
     *
     * @param afterChange the after change
     * @throws IdOfResourceNotFoundException the id of resource not found exception
     */
    public void update(Publisher afterChange) throws IdOfResourceNotFoundException {
        Publisher beforeChange = this.repository.findOne(afterChange.getId());
        if (beforeChange == null) {
            throw new IdOfResourceNotFoundException();
        } else {
            this.repository.saveAndFlush(afterChange);
        }
    }

    /**
     * Partial Update.
     *
     * @param id           the id
     * @param attributeKVs the attribute k vs
     * @throws IdOfResourceNotFoundException    the id of resource not found exception
     * @throws EntityAttributeNotFoundException the entity attribute not found exception
     */
    public void update(String id, Iterable<Map.Entry<String, Object>> attributeKVs) throws IdOfResourceNotFoundException, EntityAttributeNotFoundException {
        Publisher target = this.repository.findOne(id);
        if (target == null) {
            throw new IdOfResourceNotFoundException();
        } else {
            List<Field> fields = Arrays.asList(Publisher.class.getDeclaredFields());
            List<Method> methods = Arrays.asList(Publisher.class.getMethods());
            List<String> methodsNames = methods.stream().map(Method::getName).collect(Collectors.toList());
            List<String> fieldNames = fields.stream().map(Field::getName).collect(Collectors.toList());
            for (Map.Entry<String, Object> attributeKV : attributeKVs) {
                int indexOfAttribute = fieldNames.indexOf(attributeKV.getKey());
                if (indexOfAttribute == -1) {
                    // if the name is not attribute
                    throw new EntityAttributeNotFoundException(Publisher.class, attributeKV.getKey());
                } else {
                    // if the name is attribute
                    try {
                        int indexOfMethod = methodsNames.indexOf("set" + VariableNameUtil.toCamel(attributeKV.getKey(), true));
                        if (indexOfMethod == -1) {
                            throw new NoSuchMethodException();
                        }
                        Method method = methods.get(indexOfMethod);
                        method.invoke(target, attributeKV.getValue());
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new EntityAttributeNotFoundException(Publisher.class, attributeKV.getKey());
                    }
                }
            }
        }

        // persistence the target
        this.repository.saveAndFlush(target);
    }
}
