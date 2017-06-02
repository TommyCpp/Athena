import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.transaction.Transactional

/**
 * Created by Tommy on 2017/6/2.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@WebAppConfiguration
open class ExceptionTest {
    @Autowired private val context: WebApplicationContext? = null

    private var mvc: MockMvc? = null

    @Before fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context!!).apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity()).build()
    }

    @Test
    fun testException() {
        mvc!!.perform(get("/api/v1/books?title=what").header("X-AUTHENTICATION", "Athena eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImV4cCI6MTQ5NTYzMjg2OH0.9PVxCqnpDbCvtzNinDRRibmh_3RVSElP6K0EHX242Qo1g5F25kOUwLpfzW8fcBI5JMvcrPznYADQYcUZa0vkwA")).andDo(print())
    }
}
