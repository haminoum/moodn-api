package com.hero.moodn.moodnapi.application.grpc

import com.hero.moodn.moodnapi.domain.api.Moods
import com.hero.moodn.moodnapi.domain.model.Mood
import com.hero.moodn.moodnapi.domain.model.MoodType
import mu.KotlinLogging

// @GrpcService
class MoodGrpcService(private val moods: Moods) {
    private val logger = KotlinLogging.logger { }
    fun create() {
        val mood = Mood(type = MoodType.HAPPY)
        moods.create(mood, "Hero")
        logger.info("Get mood")
    }
}
