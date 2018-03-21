package com.athena.service.borrow

import com.athena.model.borrow.Borrow
import com.athena.model.copy.SimpleCopy
import com.athena.model.message.TemplateMessage
import com.athena.model.security.User
import com.athena.repository.jpa.BorrowRepository
import com.athena.repository.mongo.CopyDamageReportRepository
import com.athena.service.message.SystemMessageService
import com.athena.util.TemplateBuilder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.Matchers
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


/**
 * Created by Tommy on 2017/12/29.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class PublicationDamagedHandlerTest {
    lateinit var publicationDamagedHandler: PublicationDamagedHandler


    private var borrowRepository: BorrowRepository = mock(BorrowRepository::class.java)
    private var templateBuilder = spy(TemplateBuilder::class.java)
    private var systemUsers = mock(Map::class.java) as MutableMap<String, User>
    private var systemMessageService = mock(SystemMessageService::class.java)
    private var copyDamageReportRepository = mock(CopyDamageReportRepository::class.java)

    @Before
    fun setup() {
        this.publicationDamagedHandler = PublicationDamagedHandler(this.borrowRepository, this.templateBuilder, "static/publication-damage-notification.html", "title", this.systemUsers, this.systemMessageService, this.copyDamageReportRepository)
    }

    @Test
    fun testHandlerDamage_ShouldSendMessage() {
        val publicationCopy = mock(SimpleCopy::class.java)
        val lastBorrow = mock(Borrow::class.java)
        val handler = mock(User::class.java)
        val user = mock(User::class.java)
        val systemInfoUser = mock(User::class.java)
        `when`(this.borrowRepository.findFirstByCopyAndEnableIsFalseOrderByUpdatedDateDesc(publicationCopy)).thenReturn(lastBorrow)
        `when`(handler.username).thenReturn("handler")
        `when`(handler.id).thenReturn(123L)
        `when`(lastBorrow.user).thenReturn(user)
        `when`(user.id).thenReturn(666L)
        `when`(user.username).thenReturn("theBorrower")
        `when`(this.systemUsers["systemInfo"]).thenReturn(systemInfoUser)

        this.publicationDamagedHandler.handleDamage(handler, publicationCopy, "")

        verify(handler).username
        verify(handler).id
        verify(lastBorrow, times(2)).user
        verify(this.systemMessageService).send(Matchers.argThat(object : ArgumentMatcher<TemplateMessage>() {
            override fun matches(arg: Any?): Boolean {
                if (arg != null && arg is TemplateMessage) {
                    val content = arg.content
                    return content.indexOf("handler") != -1 && content.indexOf("123") != -1 && content.indexOf("666") != -1 && content.indexOf("$") == -1
                }
                return false
            }
        }))
    }
}