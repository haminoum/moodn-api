package com.hero.moodn.domain.logic

import com.hero.moodn.domain.api.Comments
import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
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
        checkIfMoodExists(comment.mood)
        commentRepository.createOrUpdateComment(comment)
        logger.debug("Comment created for mood ${comment.mood}")
    }

    @Transactional
    override fun update(comment: Comment) {
        checkIfMoodExists(comment.mood)
        commentRepository.createOrUpdateComment(comment)
        logger.debug("Comment updated for mood ${comment.mood} by ${comment.mood}")
    }

    private fun checkIfMoodExists(moodId: MoodId) =
        moodRepository.find(moodId) ?: throw NoSuchElementException("Mood $moodId not found")

    @Transactional
    override fun delete(commentId: CommentId) {
        commentRepository.delete(commentId)
        logger.debug("Comment $commentId deleted")
    }
}
