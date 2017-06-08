package com.athena.service

import com.athena.model.Book
import com.athena.repository.BookRepository
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import javax.transaction.Transactional

/**
 * Created by tommy on 2017/4/26.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:books.xml", "classpath:publishers.xml")
open class BookServiceTest {
    @Autowired private val service: BookService? = null

    @Autowired private val repository: BookRepository? = null

    @Before fun setup() {

    }

    @Test fun testSearchByName() {
        val keywords = arrayOf("埃里克森", "程序设计")
        val pageable = PageRequest(0, 20)
        val result = service!!.searchBookByName(pageable, keywords)
        val excepts = arrayOf(repository!!.findOne(9783158101891L), repository.findOne(9787111124444L), repository.findOne(9787111125643L))
        Assert.assertEquals(3, result.totalElements) // The total result should be 3
        for (except in excepts) {
            Assert.assertTrue(result.content.contains(except))
        }
    }

    @Test fun testSearchByFullName() {
        val keyword = "C程序设计"
        val pageable = PageRequest(0, 20)
        val result = service!!.searchBookByFullName(pageable, keyword)
        val excepts = repository!!.findOne(9787111124444L)
        Assert.assertEquals(1, result.totalElements)
        Assert.assertEquals(excepts, result.content[0])
    }

    @Test fun testSearchByAuthors() {
        val authors = arrayOf("Dneig dlsa", "Rdlf dls")
        val pageable = PageRequest(0, 20)
        val result = service!!.searchBookByAuthors(pageable, authors)
        val excepts = HashSet<Book>()
        excepts.add(repository!!.findOne(9783158101896L))
        excepts.add(repository.findOne(9783158101897L))
        Assert.assertEquals(excepts, HashSet<Book>(result.content))
//        TODO: Assert the correction when reverse the sequence of author

    }

}
