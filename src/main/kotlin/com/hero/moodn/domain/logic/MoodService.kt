package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.domain.spi.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MoodService(private val moodRepository: MoodRepository, private val userRepository: UserRepository) : Moods {
    private val logger = KotlinLogging.logger {}

    override fun add(mood: Mood, userId: UserId) {
        addAll(listOf(mood), userId)
    }

    override fun addAll(moods: List<Mood>, userId: UserId) {
        val user = userRepository.find(userId) ?: throw NoSuchElementException("User $userId doesn't exist")
        moodRepository.createAll(moods, user.id)
        logger.info("Mood created for $userId")
    }
}
