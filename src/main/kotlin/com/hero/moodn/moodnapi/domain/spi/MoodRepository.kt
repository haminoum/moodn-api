package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface MoodRepository {

    /**
     * Creates a new mood
     * @param mood to be created
     * @param userId of user mood is created for
     * @return the Mood created
     */
    fun create(mood: Mood, userId: UserId): Mood

    /**
     * Finds a mood by id
     * @param moodId of mood
     * @return the Mood found
     */
    fun find(id: MoodId): Mood?

    /**
     * Creates multiples moods
     * @param moods list to be created
     * @param userId of user mood is created for
     */
    fun createAll(moods: List<Mood>, id: UserId)
}
