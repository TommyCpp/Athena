package com.athena.model.publication.search

import com.athena.repository.jpa.JournalRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener

/**
 * Created by Tommy on 2018/3/18.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class, TransactionalTestExecutionListener::class)
@DatabaseSetup("classpath:journals.xml")
class JournalSearchVoTest {
    @Autowired
    lateinit var journalRepository: JournalRepository

    @Test
    fun testGetSpecification() {
        val journalSearchVo = JournalSearchVo()
        journalSearchVo.year = 2016
        journalSearchVo.issn = "03718474"

        val result_1 = this.journalRepository.findAll(journalSearchVo.specification)
        Assert.assertEquals(3, result_1.count())

    }

    @Test
    fun testJsonSerializerAndDeSerializer() {
        //test serializer
        val journalSearchVo = JournalSearchVo()
        journalSearchVo.titles = arrayOf("test1", "test2")
        journalSearchVo.count = 20
        journalSearchVo.page = 4
        journalSearchVo.lastCursor = 6
        journalSearchVo.issn = "109123102"
        journalSearchVo.issue = 12

        System.out.println(ObjectMapper().writeValueAsString(journalSearchVo))

//        //test deserializer
        val json = "{\"publisherName\":null,\"language\":null,\"count\":20,\"page\":4,\"lastCursor\":6,\"issn\":\"109123102\",\"year\":null,\"issue\":12,\"title\":[\"test4\",\"test2\"]}"
        val deserializerJournalSearchVo = ObjectMapper().readValue(json, JournalSearchVo::class.java)
        Assert.assertTrue(deserializerJournalSearchVo.titles.contains("test4"))
        Assert.assertTrue(deserializerJournalSearchVo.pageable.pageSize == 20)
        Assert.assertTrue(deserializerJournalSearchVo.issn == "109123102")
    }
}