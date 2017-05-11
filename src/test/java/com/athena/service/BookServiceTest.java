package com.athena.service;

import com.athena.model.Book;
import com.athena.repository.BookRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;

/**
 * Created by tommy on 2017/4/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@DatabaseSetup({"classpath:books.xml", "classpath:publishers.xml"})
public class BookServiceTest {
    @Autowired
    private BookService service;

    @Autowired
    private BookRepository repository;

    @Before
    public void setup() {

    }

    @Test
    public void testSearchByName() {
        String[] keywords = {
                "埃里克森", "程序设计"
        };
        Pageable pageable = new PageRequest(0, 20);
        Page<Book> result = service.searchBookByName(pageable, keywords);
        Book[] excepts = {repository.findOne(9783158101891L), repository.findOne(9787111124444L), repository.findOne(9787111125643L)};
        Assert.assertEquals(3, result.getTotalElements()); // The total result should be 3
        for (Book except : excepts) {
            Assert.assertTrue(result.getContent().contains(except));
        }
    }

}
