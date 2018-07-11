package com.athena.aspect;

import com.athena.exception.http.IllegalReturnRequest;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.service.borrow.BorrowService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Tommy on 2017/11/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotNullArgumentAspectExceptionTest {

    @Autowired
    public BorrowService borrowService;

    @Test
    public void testAspect_ShouldThrowNullPointerException() throws IllegalReturnRequest, ResourceNotFoundByIdException {
        int counter = 0;
        try {
            borrowService.returnCopy(null, null, null);
        } catch (NullPointerException e) {
            counter += 1;
        }
        try {
            borrowService.get((String) null);
        } catch (NullPointerException e) {
            counter += 1;
        }

        Assert.assertEquals(2, counter);
    }
}
