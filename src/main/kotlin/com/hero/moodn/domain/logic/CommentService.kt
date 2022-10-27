package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Comments
import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.spi.CommentRepository
import com.hero.moodn.domain.spi.MoodRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CommentService(private val moodRepository: MoodRepository, private val commentRepository: CommentRepository) :
    Comments {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun create(comment: Comment) {
        val mood = findMood(comment.mood)
        commentRepository.createOrUpdateComment(comment)
        logger.debug("Comment created for mood ${mood.id} by ${comment.mood}")
    }

    @Transactional
    override fun update(moodId: MoodId, comment: Comment) {
        val commentsMood = findMood(comment.mood)
        commentRepository.createOrUpdateComment(comment)
        logger.debug("Comment updated for mood $moodId by ${comment.mood}")
    }

    @Transactional
    override fun delete(comment: Comment, moodId: MoodId) {
        val commentsMood = findMood(comment.mood)
        commentRepository.delete(comment.id)
        logger.debug("Comment deleted for mood $moodId by ${comment.mood}")
    }

    private fun findMood(moodId: MoodId): Mood =
        moodRepository.find(moodId) ?: throw NoSuchElementException("Mood $moodId not found")
}
