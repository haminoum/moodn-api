package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface MoodRepository {

    /**
     * Finds a mood by id
     * @param moodId of mood
     * @return the Mood found
     */
    fun find(moodId: MoodId): Mood?

    /**
     * Adds multiples moods
     * @param moods list to be created
     * @param userId of user mood is created for
     */
    fun createAll(moods: List<Mood>, userId: UserId)

    /**
     * Creates or updates a comment for a [Mood]
     * @param comment [Comment] to be created
     * @return [Comment]
     */
    fun createOrUpdateComment(comment: Comment): Comment?

    /**
     * Updates a [Mood]
     * @param moodType to be updated
     * @return [Mood]
     */
    fun updateType(moodId: MoodId, moodType: MoodType): Mood?

    /**
     * Deletes a [Mood]
     * @param moodId of [Mood] to be deleted
     * @return [Boolean] value if success
     */
    fun delete(moodId: MoodId): Boolean

    /**
     * Finds a comment by id
     * @param commentId of [Comment]
     * @return the [Comment] found
     */
    fun findComment(commentId: CommentId): Comment?

    /**
     * Deletes a [Comment]
     * @param commentId of [Comment]
     * @return boolean if delete was success
     */
    fun deleteComment(commentId: CommentId): Boolean
}
