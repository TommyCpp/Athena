package com.athena.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.multipart.support.MissingServletRequestPartException
import java.util.*

/**
 * Created by tommy on 2017/5/16.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
open class PageableHeaderServiceTest {
    @Autowired
    private val service: PageableHeaderService? = null

    @Before
    fun setup() {

    }

    @Test
    fun testSetHeader() {
        var request = MockHttpServletRequest()
        request.serverName = "www.example.com"
        request.requestURI = "/books"
        request.queryString = "author=test,test&last_cursor=555&page=4"
        var response = MockHttpServletResponse()
        var pageable: Pageable = PageRequest(0, 20)
        var pageResult: MutableList<Int> = ArrayList()
        for (i in 0..99) {
            pageResult.add(i)
        }
        var page = PageImpl(pageResult, pageable, 100)
        try {
            service!!.setHeader(page, request, response)
        } catch (e: MissingServletRequestPartException) {
            e.printStackTrace()
        }

        Assert.assertEquals(java.lang.Long.toString(page.totalElements), response.getHeader("X-Total-Count"))
        Assert.assertEquals("<http://www.example.com/books?page=1&author=test,test>; rel=\"next\",<http://www.example.com/books?page=4&author=test,test>; rel=\"last\",<http://www.example.com/books?page=0&author=test,test>; rel=\"first\"", response.getHeader("Links"))

        request = MockHttpServletRequest()
        request.serverName = "www.example.com"
        request.requestURI = "/books"
        request.queryString = "author=test,test&page=3"
        response = MockHttpServletResponse()
        pageable = PageRequest(3, 20)
        pageResult = ArrayList<Int>()
        for (i in 0..99) {
            pageResult.add(i)
        }
        page = PageImpl(pageResult, pageable, 100)
        try {
            service!!.setHeader(page, request, response)
        } catch (e: MissingServletRequestPartException) {
            e.printStackTrace()
        }

        Assert.assertEquals(java.lang.Long.toString(page.totalElements), response.getHeader("X-Total-Count"))
        Assert.assertEquals("<http://www.example.com/books?page=4&author=test,test>; rel=\"next\",<http://www.example.com/books?page=2&author=test,test>; rel=\"previous\",<http://www.example.com/books?page=4&author=test,test>; rel=\"last\",<http://www.example.com/books?page=0&author=test,test>; rel=\"first\"", response.getHeader("Links"))

    }


}
