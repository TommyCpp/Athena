package com.athena.service;

import com.athena.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/3/28.
 */
@Service
public class PublisherService {

    private PublisherRepository repository;

    @Autowired
    public PublisherService(PublisherRepository repository) {

        this.repository = repository;
    }
}
