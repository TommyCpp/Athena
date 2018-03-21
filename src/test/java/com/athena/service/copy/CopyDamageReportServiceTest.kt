package com.athena.service.copy

import com.athena.model.copy.CopyDamageReport
import com.athena.repository.mongo.CopyDamageReportRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.MimeType
import org.springframework.web.multipart.MultipartFile

/**
 * Created by Tommy on 2018/2/13.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class CopyDamageReportServiceTest {

    lateinit var copyDamageReportRepository: CopyDamageReportRepository
    lateinit var copyDamageReportService: CopyDamageReportService

    @Before
    fun setup() {
        this.copyDamageReportRepository = mock(CopyDamageReportRepository::class.java)
        this.copyDamageReportService = CopyDamageReportService(copyDamageReportRepository)

        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun testAddImage_ShouldCallSetImageAndSaveCopyDamageReport() {
        val file = mock(MultipartFile::class.java)
        `when`(file.contentType).thenReturn("img/gif")
        `when`(file.inputStream).thenReturn(null)
        val copyDamageReport = mock(CopyDamageReport::class.java)
        `when`(this.copyDamageReportRepository.setImageAndSaveCopyDamageReport(eq(copyDamageReport), eq(null), any())).thenReturn(copyDamageReport)
        val result = this.copyDamageReportService.addImage(copyDamageReport, file)

        verify(this.copyDamageReportRepository).setImageAndSaveCopyDamageReport(eq(copyDamageReport), any(), eq(MimeType.valueOf("img/gif")))
    }
}