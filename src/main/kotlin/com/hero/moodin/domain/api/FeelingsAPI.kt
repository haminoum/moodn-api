package com.hero.moodin.domain.api

import com.hero.feelin.domain.model.Mood

interface FeelingsAPI {
    fun create(mood: Mood)
}
