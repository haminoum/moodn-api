package com.hero.moodin.domain.api

import com.hero.feelin.domain.model.Mood

interface MoodsAPI {
    fun create(mood: Mood, username: String)
    fun createAll(moods: Set<Mood>, username: String)
}
