package com.hero.moodn.moodnapi.domain.api

import com.hero.moodn.moodnapi.domain.model.Mood

interface Moods {
    fun create(mood: Mood, username: String)
    fun createAll(moods: List<Mood>, username: String)
}
