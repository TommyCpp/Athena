package com.athena.service

import com.athena.model.Borrow
import com.athena.repository.jpa.BorrowRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Tommy on 2017/11/10.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
open class BorrowServiceTest {
    @Mock
    lateinit var borrowRepository: BorrowRepository

    @InjectMocks
    lateinit var borrowService: BorrowService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAdd() {
        val borrow = Borrow()
        this.borrowService.add(borrow)

        verify(this.borrowRepository).save(borrow)
    }
}