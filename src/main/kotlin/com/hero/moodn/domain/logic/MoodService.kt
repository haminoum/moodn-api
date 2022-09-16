package com.hero.moodn.domain.logic

import com.hero.feelin.domain.model.Mood
import com.hero.moodn.domain.api.MoodsAPI
import com.hero.moodn.domain.spi.MoodSPI
import com.hero.moodn.domain.spi.UserSPI
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MoodService(private val moodsSPI: MoodSPI, private val userSPI: UserSPI) : MoodsAPI {
    val logger = KotlinLogging.logger {}

    override fun create(mood: Mood, username: String) {
        val user = userSPI.find(username) ?: throw IllegalArgumentException("User $username does'nt exist")
        moodsSPI.create(mood, user.id)
        logger.info("Mood created for $username")
    }

    override fun createAll(moods: Set<Mood>, username: String) {
        val user = userSPI.find(username) ?: throw IllegalArgumentException("User $username does'nt exist")
        moodsSPI.createAll(moods, user.id)
        logger.info("Moods created for $username")
    }
}
