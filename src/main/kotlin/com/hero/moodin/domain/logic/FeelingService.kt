package com.hero.moodin.domain.logic

import com.hero.feelin.domain.model.Mood
import com.hero.moodin.domain.api.FeelingsAPI
import com.hero.moodin.domain.spi.MoodSPI
import org.springframework.stereotype.Component

@Component
class FeelingService(private val feelings: MoodSPI) : FeelingsAPI {
    override fun create(mood: Mood) {
        feelings.create(mood)
    }
}
