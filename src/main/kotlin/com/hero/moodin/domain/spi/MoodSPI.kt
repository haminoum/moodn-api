package com.hero.moodin.domain.spi

import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodId
import org.springframework.stereotype.Repository

@Repository
interface MoodSPI {

    /**
     * Creates a new feel-in
     */
    fun create(mood: Mood): Mood

    /**
     * Finds a feel-in via id
     */
    fun find(id: MoodId): Mood?
}
