package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.Comment
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
     * Updates a comment for a [Mood]
     * @param moodId of [Mood] to be updated
     * @param comment [Comment] to be created
     * @return [Mood]
     */
    fun update(moodId: MoodId, comment: Comment): Mood?

    /**
     * Updates a [Mood]
     * @param moodType to be updated
     * @return [Mood]
     */
    fun update(moodId: MoodId, moodType: MoodType): Mood?
}
