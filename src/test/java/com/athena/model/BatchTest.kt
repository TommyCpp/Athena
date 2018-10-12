package com.athena.model

import com.athena.model.common.Batch
import com.athena.repository.mongo.BatchRepository
import com.lordofthejars.nosqlunit.annotation.UsingDataSet
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import java.util.*


/**
 * Created by Tommy on 2017/8/18.
 *
 */
@RunWith(SpringRunner::class)
@SpringBootTest
open class BatchTest {
    @Autowired
    private val applicationContext: ApplicationContext? = null

    @Autowired
    lateinit var repository: BatchRepository

    @get:Rule
    var mongoRule: MongoDbRule = newMongoDbRule().defaultSpringMongoDb("athena")

    @Test
    @UsingDataSet(locations = arrayOf("/batch.json"), loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    fun testSaveOne() {
        System.out.println(Calendar.getInstance().time.toString())
        var batch: Batch = Batch(UUID.randomUUID().toString(), "Book", Calendar.getInstance().time, mutableListOf("test1", "test1"))
        Assert.assertNotNull(this.repository.findOne("bac0546a-9001-4f65-8417-c6acdc7d5e6c"))
        Assert.assertEquals(2, this.repository.findAll().count())
        this.repository.save(batch)
        Assert.assertNotNull(this.repository.findOne(batch.id))
    }

    @Test
    @UsingDataSet(locations = arrayOf("/batch.json"), loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    fun test() {
        System.out.println(Calendar.getInstance().time.toString())
        var batch: Batch = Batch(UUID.randomUUID().toString(), "Book", Calendar.getInstance().time, mutableListOf("test1", "test1"))
        Assert.assertNotNull(this.repository!!.findOne("bac0546a-9001-4f65-8417-c6acdc7d5e6c"))
        Assert.assertEquals(2, this.repository.findAll().count())
        this.repository.save(batch)
        Assert.assertNotNull(this.repository.findOne(batch.id))
    }


}