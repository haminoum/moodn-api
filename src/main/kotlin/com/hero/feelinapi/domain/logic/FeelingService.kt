package com.hero.feelinapi.domain.logic

import com.hero.feelin.domain.model.Feeling
import com.hero.feelinapi.domain.api.FeelingsAPI
import com.hero.feelinapi.domain.spi.FeelingSPI
import org.springframework.stereotype.Component

@Component
class FeelingService(private val feelings: FeelingSPI) : FeelingsAPI {
    override fun createFeeling(feeling: Feeling) {
        feelings.create(feeling)
    }
}
