package study.project.userservice.util.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object JwtUtil {

    fun createToken(
        claim: JwtClaim,
        properties: JwtProperties,
    ) =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("email", claim.email)
            .withClaim("profileUrl", claim.profileUrl)
            .withClaim("username", claim.username)
            .sign(HMAC256(properties.secret))

    fun decode(
        token: String,
        secret: String,
        issuer: String,
    ): DecodedJWT {
        val algorithm = HMAC256(secret)

        val verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()

        return verifier.verify(token)
    }
}
