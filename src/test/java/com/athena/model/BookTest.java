package com.athena.model;

import com.athena.repository.BookRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.transaction.Transactional;

/**
 * Created by tommy on 2017/3/29.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup({"classpath:books.xml","classpath:publishers.xml"})
public class BookTest {
    @Autowired
    private BookRepository repository;

    @Before
    public void setup(){
    }

    @Test
    public void testGetPublisher(){
        Book book = repository.findOne(9787111124444L);
        Assert.assertEquals(book.getPublisher().getName(), "测试出版社");
    }

    @Test
    public void testSaveBook(){
        Book book = repository.findOne(9787111124444L);
        book.setTitle("测试书就");
        repository.save(book);
        Assert.assertEquals("ce,shi,shu,jiu", repository.findOne(9787111124444L).getTitlePinYin());
    }
}
