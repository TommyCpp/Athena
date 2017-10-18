package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.ResourceNotDeletable;
import com.athena.model.Publication;
import com.athena.model.Publisher;
import com.athena.repository.jpa.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by tommy on 2017/3/28.
 */
@Service
public class PublisherService {

    private PublisherRepository repository;
    private BookService bookService;


    /**
     * Instantiates a new Publisher service.
     *
     * @param repository the repository
     * @param bookService
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
    public Set<Publisher> getPublishers(Iterable<Publication> publications) {
        return StreamSupport.stream(publications.spliterator(), true).map(Publication::getPublisher).collect(Collectors.toSet());
    }

    /**
     * Gets publisher by id
     *
     * @param id the id
     * @return the publisher
     * @throws IdOfResourceNotFoundException the id of resource not found exception
     */
    public Publisher getPublisher(String id) throws IdOfResourceNotFoundException {
        Publisher publisher = this.repository.findOne(id);
        if (publisher == null) {
            throw new IdOfResourceNotFoundException();
        } else {
            return publisher;
        }
    }


    @Transactional
    public void delete(String id) throws IdOfResourceNotFoundException, ResourceNotDeletable {
        Publisher publisher = this.repository.findOne(id);
        if(publisher == null){
            throw new IdOfResourceNotFoundException();
        }
        //check if the book is clear and delete
        this.bookService.delete(publisher.getBooks());
        this.repository.delete(publisher);
    }
}
