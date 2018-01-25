package com.athena;

import com.athena.model.publication.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.BookGenerator;
import util.RandomChineseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AthenaApplicationTests {

    @Test
    public void test() {
        Assert.assertEquals(true, true);
    }

    @Test
    public void testChinese() {
        RandomChineseService service = new RandomChineseService();
        String result = service.generateChinese(16);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testBookGenerator() throws JsonProcessingException {
        BookGenerator generator = new BookGenerator();
        Book book = generator.generateBook();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(book));
        Assert.assertNotNull(book);
    }
}
