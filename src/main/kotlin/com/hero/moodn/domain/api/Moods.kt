package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.UserId

interface Moods {
    /**
     * Creates a new mood
     * @param mood to be created
     * @param userId of user mood is created for
     */
    fun create(mood: Mood, userId: UserId)

    /**
     * Creates a comment to an existing mood
     * @param moodId ID of the [Mood] to which a comment should be added
     * @param comment the [Comment] to be added
     */
    fun createComment(moodId: MoodId, comment: Comment)

    /**
     * Creates multiples moods
     * @param moods list to be created
     * @param userId of user mood is created for
     */
    fun createAll(moods: List<Mood>, userId: UserId)

    /**
     * Updates a [Mood]
     * @param moodType to be updated
     */
    fun update(moodId: MoodId, moodType: MoodType)
}
