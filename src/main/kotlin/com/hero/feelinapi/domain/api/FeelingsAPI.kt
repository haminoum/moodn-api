package com.hero.feelinapi.domain.api

import com.hero.feelin.domain.model.Feeling

interface FeelingsAPI {
    fun createFeeling(feeling: Feeling)
}
