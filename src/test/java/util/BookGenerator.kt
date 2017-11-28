package util

import com.athena.model.Book
import org.apache.commons.lang3.RandomStringUtils
import java.sql.Date
import java.util.*

/**
 * Created by Tommy on 2017/8/8.
 *
 */
class BookGenerator {
    private val token: String = "jllsdv;o9"
    private val randomChineseService: RandomChineseService = RandomChineseService()
    private val publisherGenerator: PublisherGenerator = PublisherGenerator()
    private val random: Random = Random(Date().time)
    private val categoryIds: List<String> = listOf("A1", "A2", "A3", "A4", "A49", "A5", "A7", "A8", "B-4", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B80", "B81", "TP278", "TP306", "TP323")

    fun generateBook(): Book {
        val book: Book = Book()
        book.isbn = this.generateIsbn().toLong()
        book.language = if (this.random.nextInt(10) > 2) "Chinese" else "English"
        book.title = this.generateTitle(book.language)
        book.subtitle = if (this.random.nextInt(10) > 5) this.generateTitle(book.language) else null
        if (this.random.nextInt(10) > 8) {
            // Translate book
            book.author = this.generateAuthor("English")
            book.translator = this.generateAuthor("Chinese")
        } else {
            book.author = this.generateAuthor()
            book.translator = null
        }
        book.publisher = publisherGenerator.generatorPublisher()
        book.publishDate = generateRandomTime()
        book.categoryId = selectCategoryId()
        book.version = 1
        book.price = (this.random.nextFloat() * 100).toDouble()
        book.introduction = RandomStringUtils.randomAlphabetic(300)
        return book
    }

    private fun selectCategoryId(): String? {
        return this.categoryIds[this.random.nextInt(this.categoryIds.count())]
    }

    private fun generateRandomTime(): Date {
        var calendar: Calendar = Calendar.getInstance()
        // Adjust day
        calendar.set(this.random.nextInt(100) + 1917, this.random.nextInt(12), this.random.nextInt(30))
        var result: Date = Date(calendar.timeInMillis)
        return result
    }

    private fun generateAuthor(language: String? = null): List<String>? {
        var authors: MutableList<String> = mutableListOf()
        val number = this.random.nextInt(4) + 1
        for (i in 1..number) {
            if (language == null) {
                if (this.random.nextInt(10) > 1) {
                    // Add Chinese author
                    authors.add(randomChineseService.generateChinese(if (this.random.nextBoolean()) 2 else 3))
                } else {
                    authors.add(RandomStringUtils.randomAlphabetic(this.random.nextInt(10) + 1))
                }
            } else if (language == "English") {
                authors.add(RandomStringUtils.randomAlphabetic(this.random.nextInt(10) + 1))
            } else {
                authors.add(RandomStringUtils.randomAlphabetic(this.random.nextInt(3) + 1))
            }
        }
        return authors
    }

    private fun generateTitle(language: String): String? {
        if (language == "Chinese") {
            //book that written in Chinese
            return randomChineseService.generateChinese(this.random.nextInt(10) + 1)
        } else {
            return RandomStringUtils.randomAlphabetic(this.random.nextInt(10) + 1)
        }
    }

    private fun generateIsbn(): String {
        var result = "978"
        val number_pool = "0123456789"
        for (i in 1..10) {
            result += number_pool[random.nextInt(10)]
        }
        return result
    }
}