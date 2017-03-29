package com.athena.model;

import com.athena.repository.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by tommy on 2017/3/29.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookTest {
    @Autowired
    private BookRepository repository;

    @Before
    public void setup(){
    }

    @Test
    public void testGetPublisher(){
        Book book = repository.findOne(7111128060L);
        Assert.assertEquals(book.getPublisher().getName(), "机械工业出版社");
    }
}
