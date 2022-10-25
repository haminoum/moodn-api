package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.domain.spi.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MoodService(private val moodRepository: MoodRepository, private val userRepository: UserRepository) : Moods {
    private val logger = KotlinLogging.logger {}

    override fun create(mood: Mood, username: String) {
        val user = userRepository.find(username) ?: throw IllegalArgumentException("User $username does'nt exist")
        moodRepository.create(mood, user.id)
        logger.info("Mood ${mood.id} created for $username")
    }

    override fun createAll(moods: List<Mood>, username: String) {
        val user = userRepository.find(username) ?: throw IllegalArgumentException("User $username does'nt exist")
        moodRepository.createAll(moods, user.id)
        logger.info("${moods.size} moods created for $username")
    }
}
