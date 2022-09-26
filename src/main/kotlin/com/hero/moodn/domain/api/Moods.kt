package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Mood

interface Moods {
    fun create(mood: Mood, username: String)
    fun createAll(moods: List<Mood>, username: String)
}
