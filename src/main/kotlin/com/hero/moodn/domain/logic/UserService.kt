package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.UsersAPI
import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.UserSPI
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UserService(private val usersSPI: UserSPI) : UsersAPI {
    val logger = KotlinLogging.logger {}

    override fun create(username: String) {
        val user = User(
            id = UserId(),
            username = username,
            createdAt = Instant.now(),
        )
        usersSPI.create(user)
        logger.info("Created user $username")
    }
}
