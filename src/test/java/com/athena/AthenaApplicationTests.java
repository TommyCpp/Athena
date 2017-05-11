package com.athena;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AthenaApplicationTests {

    @Test
    public void test() {
        Assert.assertEquals(true, true);
    }

}
