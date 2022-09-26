package com.hero.moodn.moodnapi.domain.spi

import com.hero.moodn.moodnapi.domain.model.Mood
import com.hero.moodn.moodnapi.domain.model.MoodId
import com.hero.moodn.moodnapi.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface MoodRepository {

    /**
     * Creates a new feel-in
     */
    fun create(mood: Mood, userId: UserId): Mood

    /**
     * Finds a mood by id
     */
    fun find(id: MoodId): Mood?
    fun createAll(moods: List<Mood>, id: UserId)
}
