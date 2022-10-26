package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.domain.spi.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MoodService(private val moodRepository: MoodRepository, private val userRepository: UserRepository) : Moods {
    private val logger = KotlinLogging.logger {}

    override fun create(mood: Mood, userId: UserId) {
        createAll(listOf(mood), userId)
    }

    @Transactional
    override fun createComment(moodId: MoodId, comment: Comment) {
        val mood = moodRepository.find(moodId) ?: throw NoSuchElementException("Mood $moodId not found")
        val user = userRepository.find(comment.author)
            ?: throw IllegalArgumentException("Comment author ${comment.author} not found")
        if (mood.user != user.id) throw IllegalArgumentException("User ${comment.author} is not expected user")
        moodRepository.updateMood(moodId, comment)
    }

    override fun createAll(moods: List<Mood>, userId: UserId) {
        val user = userRepository.find(userId) ?: throw NoSuchElementException("User $userId not found")
        moodRepository.createAll(moods, user.id)
        logger.info("Mood created for $userId")
    }
}
