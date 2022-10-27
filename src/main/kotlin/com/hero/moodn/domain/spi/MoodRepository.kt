package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface MoodRepository {

    fun find(moodId: MoodId): Mood?

    fun createAll(moods: List<Mood>, userId: UserId)

    fun updateType(moodId: MoodId, moodType: MoodType): Mood?

    fun delete(moodId: MoodId): Boolean
}
