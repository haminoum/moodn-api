package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.User
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

    override fun createAll(moods: List<Mood>, userId: UserId) {
        validateUser(userId)
        moodRepository.createAll(moods, userId)
        logger.debug("Mood created for $userId")
    }

    override fun updateType(mood: Mood, userId: UserId) {
        validateUsers(mood.id, userId)
        val existingMood = findMood(mood.id)
        val differentTypes = (existingMood.type != mood.type)
        if (differentTypes) moodRepository.updateType(mood.id, mood.type)
        logger.debug("Mood ${mood.id} update for $userId")
    }

    override fun delete(moodId: MoodId, userId: UserId): Boolean {
        validateUsers(moodId, userId)
        return moodRepository.delete(moodId)
    }

    @Transactional
    override fun createComment(comment: Comment) {
        val mood = findMood(comment.mood)
        moodRepository.createOrUpdateComment(comment)
        logger.debug("Comment created for mood ${mood.id} by ${comment.mood}")
    }

    @Transactional
    override fun updateComment(moodId: MoodId, comment: Comment) {
        val commentsMood = findMood(comment.mood)
        validateUsers(moodId, commentsMood.user)
        moodRepository.createOrUpdateComment(comment)
        logger.debug("Comment updated for mood $moodId by ${comment.mood}")
    }

    override fun deleteComment(comment: Comment, moodId: MoodId) {
        val commentsMood = findMood(comment.mood)
        validateUsers(moodId, commentsMood.user)
        moodRepository.deleteComment(comment.id)
        logger.debug("Comment deleted for mood $moodId by ${comment.mood}")
    }

    private fun validateUser(userId: UserId) {
        findUser(userId)
    }

    private fun validateUsers(moodId: MoodId, userId: UserId) {
//
//        val user = findUser(mood.user)
//        if (mood.user != user.id) throw IllegalArgumentException("Invalid User")
    }

    private fun findMood(moodId: MoodId): Mood =
        moodRepository.find(moodId) ?: throw NoSuchElementException("Mood $moodId not found")

    private fun findUser(userId: UserId): User =
        userRepository.find(userId) ?: throw throw NoSuchElementException("User $userId not found")
}
