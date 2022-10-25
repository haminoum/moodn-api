package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface MoodRepository {

    /**
     * Finds a mood by id
     * @param moodId of mood
     * @return the Mood found
     */
    fun find(id: MoodId): Mood?

    /**
     * Adds multiples moods
     * @param moods list to be created
     * @param userId of user mood is created for
     */
    fun createAll(moods: List<Mood>, id: UserId)
}
