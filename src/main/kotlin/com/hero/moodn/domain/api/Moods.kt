package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.UserId

interface Moods {
    /**
     * Adds a new mood
     * @param mood to be created
     * @param userId of user mood is created for
     */
    fun add(mood: Mood, userId: UserId)

    /**
     * Creates multiples moods
     * @param moods list to be created
     * @param userId of user mood is created for
     */
    fun addAll(moods: List<Mood>, userId: UserId)
}
