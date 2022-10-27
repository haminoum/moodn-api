package com.hero.moodn.application.http

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.UserId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("moods")
class MoodController(private val moods: Moods) {

    @PostMapping("create")
    fun addMood(@RequestParam type: String, @RequestParam username: String) {
        val userId = UserId(username)
        val mood = Mood(id = MoodId(), type = MoodType.HAPPY, user = userId)
        moods.create(mood, userId)
    }
}
