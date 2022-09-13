package com.hero.feelinapi.domain.spi

import com.hero.feelin.domain.model.Feeling
import com.hero.feelin.domain.model.FeelingId
import org.springframework.stereotype.Repository

@Repository
interface FeelingSPI {

    /**
     * Creates a new feel-in
     */
    fun create(feeling: Feeling): Feeling

    /**
     * Finds a feel-in via id
     */
    fun find(id: FeelingId): Feeling?
}
