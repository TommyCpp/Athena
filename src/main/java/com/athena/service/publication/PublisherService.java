package com.athena.service.publication;

import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.exception.internal.EntityAttributeNotFoundException;
import com.athena.model.publication.Publication;
import com.athena.model.publication.Publisher;
import com.athena.repository.jpa.PublisherRepository;
import com.athena.service.ModelCRUDService;
import com.athena.util.VariableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by tommy on 2017/3/28.
 */
@Service
public class PublisherService implements ModelCRUDService<Publisher, String> {

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
     * Gets publisher set from publications.
     *
     * @param publications the publications
     * @return the publishers
     */
    public Set<Publisher> getByPublications(Iterable<Publication> publications) {
        return StreamSupport.stream(publications.spliterator(), true).map(Publication::getPublisher).collect(Collectors.toSet());
    }

    @Override
    public Publisher add(Publisher publisher) {
        return this.repository.saveAndFlush(publisher);
    }

    /**
     * Gets publisher by id
     *
     * @param id the id
     * @return the publisher
     * @throws ResourceNotFoundByIdException the id of resource not found exception
     */
    public Publisher get(String id) throws ResourceNotFoundByIdException {
        Publisher publisher = this.repository.findOne(id);
        if (publisher == null) {
            throw new ResourceNotFoundByIdException();
        } else {
            return publisher;
        }
    }

    @Override
    public void delete(Publisher publisher) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        Objects.requireNonNull(publisher);
        this.delete(publisher.getId());
    }

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundByIdException the id of resource not found exception
     * @throws ResourceNotDeletable          the resource not deletable
     */
    @Override
    @Transactional
    public void delete(String id) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        Publisher publisher = this.repository.findOne(id);
        if (publisher == null) {
            throw new ResourceNotFoundByIdException();
        }
        //check if the book is clear and delete
        this.bookService.delete(publisher.getBooks());
        this.repository.delete(publisher);
    }


    /**
     * Update.
     *
     * @param afterChange the after change
     * @throws ResourceNotFoundByIdException the id of resource not found exception
     */
    public Publisher update(Publisher afterChange) throws ResourceNotFoundByIdException {
        Publisher beforeChange = this.repository.findOne(afterChange.getId());
        if (beforeChange == null) {
            throw new ResourceNotFoundByIdException();
        } else {
            return this.repository.saveAndFlush(afterChange);
        }
    }

    /**
     * Partial Update.
     *
     * @param id           the id
     * @param attributeKVs the attribute k vs
     * @throws ResourceNotFoundByIdException    the id of resource not found exception
     * @throws EntityAttributeNotFoundException the entity attribute not found exception
     */
    public Publisher update(String id, Iterable<Map.Entry<String, Object>> attributeKVs) throws ResourceNotFoundByIdException, EntityAttributeNotFoundException {
        Publisher target = this.repository.findOne(id);
        if (target == null) {
            throw new ResourceNotFoundByIdException();
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
        return this.repository.saveAndFlush(target);
    }
}
