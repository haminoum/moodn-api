package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Users
import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class UserService(private val userRepository: UserRepository) : Users {
    private val logger = KotlinLogging.logger {}
    override fun create(user: User) {
        userRepository.create(user)
        logger.info("Created user ${user.username}")
    }

    override fun find(userId: UserId): User? {
        return userRepository.find(userId) ?: throw NoSuchElementException("User $userId doesn't exist")
    }
}
