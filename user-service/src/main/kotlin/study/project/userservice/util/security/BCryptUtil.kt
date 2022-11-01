package study.project.userservice.util.security

import at.favre.lib.crypto.bcrypt.BCrypt

object BCryptUtil {

    fun hash(password: String) =
        BCrypt.withDefaults()
            .hashToString(12, password.toCharArray())

    fun verify(
        password: String,
        hashedPassword: String
    ) =
        BCrypt.verifyer()
            .verify(password.toCharArray(), hashedPassword)
            .verified
}