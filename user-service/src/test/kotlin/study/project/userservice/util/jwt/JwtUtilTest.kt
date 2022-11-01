package study.project.userservice.util.jwt

import com.auth0.jwt.interfaces.DecodedJWT
import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class JwtUtilTest(
    @Autowired
    private val jwtProperties: JwtProperties
) {

    private val log = KotlinLogging.logger {}

    private val jwtClaim = JwtClaim(
        userId = 1L,
        email = "dev@gmail.com",
        profileUrl = "profile.jpg",
        username = "개발자"
    )

    private fun getToken() = JwtUtil.createToken(jwtClaim, jwtProperties)

    @Test
    fun createTokenTest() {

        val token = this.getToken()
        assertNotNull(token)

        log.info { "token: $token" }
    }

    @Test
    fun decodeTest() {

        val token = this.getToken()

        val decode: DecodedJWT = JwtUtil.decode(token, secret = jwtProperties.secret, issuer = jwtProperties.issuer)

        with(decode) {
            log.info { "claims: $claims" }

            val userId = claims["userId"]!!.asLong()
            assertEquals(jwtClaim.userId, userId)

            val email = claims["email"]!!.asString()
            assertEquals(jwtClaim.email, email)

            val profileUrl = claims["profileUrl"]?.asString()
            assertEquals(jwtClaim.profileUrl, profileUrl)

            val username = claims["username"]!!.asString()
            assertEquals(jwtClaim.username, username)
        }
    }
}