package com.athena.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tommy on 2017/5/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageableHeaderServiceTest {
    @Autowired
    private PageableHeaderService service;

    @Before
    public void setup() {

    }

    @Test
    public void testSetHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.example.com");
        request.setRequestURI("/books");
        request.setQueryString("author=test,test&last_cursor=555&page=4");
        MockHttpServletResponse response = new MockHttpServletResponse();
        Pageable pageable = new PageRequest(0, 20);
        List<Integer> pageResult = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            pageResult.add(i);
        }
        PageImpl page = new PageImpl(pageResult, pageable, 100);
        try {
            service.setHeader(page, request, response);
        } catch (MissingServletRequestPartException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(Long.toString(page.getTotalElements()), response.getHeader("X-Total-Count"));
        Assert.assertEquals("<http://www.example.com/books?page=1&author=test,test>; rel=\"next\",<http://www.example.com/books?page=4&author=test,test>; rel=\"last\",<http://www.example.com/books?page=0&author=test,test>; rel=\"first\"", response.getHeader("Links"));

        request = new MockHttpServletRequest();
        request.setServerName("www.example.com");
        request.setRequestURI("/books");
        request.setQueryString("author=test,test&page=3");
        response = new MockHttpServletResponse();
        pageable = new PageRequest(3, 20);
        pageResult = new ArrayList<>();
        for(int i=0;i<100;i++){
            pageResult.add(i);
        }
        page = new PageImpl(pageResult, pageable, 100);
        try {
            service.setHeader(page, request, response);
        } catch (MissingServletRequestPartException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(Long.toString(page.getTotalElements()), response.getHeader("X-Total-Count"));
        Assert.assertEquals("<http://www.example.com/books?page=4&author=test,test>; rel=\"next\",<http://www.example.com/books?page=2&author=test,test>; rel=\"previous\",<http://www.example.com/books?page=4&author=test,test>; rel=\"last\",<http://www.example.com/books?page=0&author=test,test>; rel=\"first\"", response.getHeader("Links"));

    }


}
