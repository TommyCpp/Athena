package com.athena.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tommy on 2017/5/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageableHeaderServiceTest {

    private Method getQueryMap;
    private Method getQuery;

    @Before
    public void setup() {
        try {
            this.getQueryMap = PageableHeaderService.class.getDeclaredMethod("getQueryMap", String.class);
            getQueryMap.setAccessible(true);
            this.getQuery = PageableHeaderService.class.getDeclaredMethod("getQuery", Map.class);
            this.getQuery.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetQueryMap() {
        String query = "author=test,test&last_cursor=4&count=20";
        Map<String, String> result = null;
        try {
            result = (Map<String, String>) this.getQueryMap.invoke(PageableHeaderService.class.newInstance(), query);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        Map<String, String> except = new HashMap<>();
        except.put("author", "test,test");
        except.put("last_cursor", "4");
        except.put("count", "20");
        Assert.assertTrue(except.equals(result));
    }

    @Test
    public void testGetQuery() {
        Map<String, String> map = new HashMap<>();
        map.put("author", "test,test");
        map.put("last_cursor", "4");
        map.put("count", "20");

        String except = "last_cursor=4&author=test,test&count=20";
        String result = null;
        try {
            result = (String) this.getQuery.invoke(PageableHeaderService.class.newInstance(), map);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(except, result);
    }

}
