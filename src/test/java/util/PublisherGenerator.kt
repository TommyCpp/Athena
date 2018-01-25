package util

import com.athena.model.publication.Publisher
import org.apache.commons.lang3.RandomStringUtils
import java.util.*

/**
 * Created by Tommy on 2017/8/8.
 *
 */
class PublisherGenerator {
    private val random: Random = Random(Date().time)
    private val locations: Array<String> = arrayOf("NewYork", "NanJing", "London", "Paris", "ShangHai", "BeiJing", "Hangzhou")
    private val service = RandomChineseService()

    fun generatorPublisher(): Publisher {
        val publisher = Publisher()
        publisher.id = RandomStringUtils.randomNumeric(3)
        publisher.location = locations[random.nextInt(locations.count())]
        publisher.name = if (random.nextInt() % 10 > 2) service.generateChinese(random.nextInt(4)) + "出版社" else RandomStringUtils.random(random.nextInt(5)) + " Publisher"
        return publisher
    }
}