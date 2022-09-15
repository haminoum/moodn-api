package com.hero.moodin.domain.logic

import com.hero.feelin.domain.model.Mood
import com.hero.moodin.domain.api.MoodsAPI
import com.hero.moodin.domain.spi.MoodSPI
import com.hero.moodin.domain.spi.UserSPI
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MoodService(private val moods: MoodSPI, private val users: UserSPI) : MoodsAPI {
    val logger = KotlinLogging.logger {}

    override fun create(mood: Mood, username: String) {
        val user = users.find(username) ?: throw IllegalArgumentException("User $username does'nt exist")
        moods.create(mood, user.id)
        logger.info("Mood created")
    }
}
