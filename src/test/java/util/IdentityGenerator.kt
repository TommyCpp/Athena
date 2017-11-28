package util

import com.athena.model.User
import com.athena.security.model.Account
import com.athena.security.model.JwtAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.RequestPostProcessor

/**
 * Created by 吴钟扬 on 2017/8/19.
 *
 */
open class IdentityGenerator{
    private fun createAuthentication(role: String): JwtAuthenticationToken {
        val encoder = BCryptPasswordEncoder()
        val user = User()
        user.identity = role
        user.username = "reader"
        user.password = encoder.encode("123456")
        user.id = 1L
        user.email = "test@test.com"
        user.wechatId = "testWechat"
        user.phoneNumber = "11111111111"
        val principal = Account(user)

        return JwtAuthenticationToken(principal, true)
    }

    public open fun authentication(role: String = "ROLE_READER"): RequestPostProcessor {
        val securityContext = SecurityContextHolder.createEmptyContext()
        securityContext.authentication = this.createAuthentication(role)

        return SecurityMockMvcRequestPostProcessors.securityContext(securityContext)
    }
}