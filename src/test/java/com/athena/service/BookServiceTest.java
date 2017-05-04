package com.athena.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Before
    void setup() {

    }

    @Test
    void testSearchByName() {

    }

}
