package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.domain.spi.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MoodService(private val moodRepository: MoodRepository, private val userRepository: UserRepository) : Moods {
    private val logger = KotlinLogging.logger {}

    override fun create(mood: Mood, userId: UserId) = createAll(listOf(mood), userId)

    override fun createAll(moods: List<Mood>, userId: UserId) {
        val user = userRepository.find(userId) ?: throw throw NoSuchElementException("User $userId not found")
        moodRepository.createAll(moods, user.id)
        logger.debug("Mood created for $userId")
    }

    override fun updateType(mood: Mood) {
        val existingMood = moodRepository.find(mood.id) ?: throw NoSuchElementException("Mood ${mood.id} not found")
        val differentTypes = (existingMood.type != mood.type)
        if (differentTypes) moodRepository.updateType(mood.id, mood.type)
        logger.debug("Mood ${mood.id} updated")
    }

    override fun delete(moodId: MoodId): Boolean = moodRepository.delete(moodId)
}
