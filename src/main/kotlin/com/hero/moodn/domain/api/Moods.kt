package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId

interface Moods {
    /**
     * Creates a new [Mood]
     * @param mood to be created
     * @param userId of [User] the [Mood] is created for
     */
    fun create(mood: Mood, userId: UserId)

    /**
     * Creates multiples moods
     * @param moods list to be created
     * @param userId of [User] the moods is created for
     */
    fun createAll(moods: List<Mood>, userId: UserId)

    /**
     * Updates a [Mood]
     * @param mood to be updated
     * @param userId of [User]
     */
    fun updateType(mood: Mood, userId: UserId)

    /**
     * Deletes a [Mood]
     * @param moodId of the [Mood] to be deleted
     * @param userId of [User]
     * @return [Boolean] value of deletion was success
     */
    fun delete(moodId: MoodId, userId: UserId): Boolean

    /**
     * Creates a [Comment] to an existing mood
     * @param comment the [Comment] to be added
     */
    fun createComment(comment: Comment)

    /**
     * Updates a [Comment] to an existing mood
     * @param moodId ID of the [Mood] to which a comment should be updated
     * @param comment the [Comment] to be added
     */
    fun updateComment(moodId: MoodId, comment: Comment)

    /**
     * Deletes a [Comment] to an existing mood
     * @param comment the [Comment] to be added
     * @param moodId ID of the [Mood] to which a comment should be deleted
     */
    fun deleteComment(comment: Comment, moodId: MoodId)
}
