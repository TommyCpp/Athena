package com.athena.service.util

import com.athena.exception.http.ResourceNotFoundByIdException
import com.athena.model.common.Batch
import com.athena.model.publication.Book
import com.athena.repository.mongo.BatchRepository
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers.argThat
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.transaction.Transactional


/**
 * Created by Tommy on 2018/7/18.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
open class BatchServiceTest {

    @Mock
    lateinit var batchRepository: BatchRepository

    @InjectMocks
    lateinit var batchService: BatchService

    lateinit var batch: Batch

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        this.batch = Batch("test", Book::class.java.simpleName, Calendar.getInstance().time, arrayListOf("test1", "test2"))
        Mockito.`when`(this.batchRepository.save(any(Batch::class.java))).thenAnswer { invocationOnMock -> invocationOnMock.arguments[0] }
    }

    @Test
    fun testAdd_ShouldAddABatch() {
        this.batchService.add(this.batch)

        verify(this.batchRepository).save(this.batch)
    }

    @Test
    fun testAdd_ShouldCreateABatchAccordingClassTypeAndUrls() {
        this.batchService.add(arrayListOf("test1.com", "test2.com"), Book::class.java)
        verify(this.batchRepository).save(argThat(object : Matcher<Batch> {
            override fun describeTo(p0: Description?) {

            }

            override fun _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            override fun describeMismatch(p0: Any?, p1: Description?) {

            }

            override fun matches(p0: Any?): Boolean {
                return p0!!::class.java == Batch::class.java && (p0 as Batch).type == "Book"
            }

        }))
    }

    @Test
    fun testGet() {
        Mockito.`when`(this.batchRepository.findOne("test")).thenReturn(this.batch)
        Assert.assertEquals(this.batch, this.batchService.get("test"))
        var flag = false
        try {
            this.batchService.get("NoneExistingBatch")
        } catch (ex: ResourceNotFoundByIdException) {
            flag = true
        }
        Assert.assertTrue(flag)
    }

    @Test
    fun testDelete() {
        this.batchService.delete(this.batch)
        verify(this.batchRepository).delete(this.batch)
    }

    @Test
    fun testUpdate(){
        this.batchService.update(this.batch)
        verify(this.batchRepository).save(this.batch)
    }


}