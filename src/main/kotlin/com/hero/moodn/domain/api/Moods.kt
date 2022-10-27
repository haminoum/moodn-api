package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId

interface Moods {

    fun create(mood: Mood, userId: UserId)

    fun createAll(moods: List<Mood>, userId: UserId)

    fun updateType(mood: Mood, userId: UserId)

    fun delete(moodId: MoodId, userId: UserId): Boolean
}
