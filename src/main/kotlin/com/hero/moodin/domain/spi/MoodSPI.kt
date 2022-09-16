package com.hero.moodin.domain.spi

import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodId
import com.hero.moodin.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface MoodSPI {

    /**
     * Creates a new feel-in
     */
    fun create(mood: Mood, userId: UserId): Mood

    /**
     * Finds a mood by id
     */
    fun find(id: MoodId): Mood?
    fun createAll(moods: Set<Mood>, id: UserId)
}
