package com.hero.moodn.moodnapi.domain.logic

import com.hero.moodn.moodnapi.domain.api.Users
import com.hero.moodn.moodnapi.domain.model.User
import com.hero.moodn.moodnapi.domain.model.UserId
import com.hero.moodn.moodnapi.domain.spi.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UserService(private val userRepository: UserRepository) : Users {
    private val logger = KotlinLogging.logger {}

    override fun create(username: String) {
        val user = User(
            id = UserId(),
            username = username,
            createdAt = Instant.now()
        )
        userRepository.create(user)
        logger.info("Created user $username")
    }
}
