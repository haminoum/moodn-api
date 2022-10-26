package com.hero.moodn.domain.model

import java.time.Instant

enum class MoodType {
    HAPPY,
    NEUTRAL,
    SAD,
}

data class Mood(
    val id: MoodId = MoodId(),
    val type: MoodType,
    val user: UserId,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
//    val comment: Comment?
) {

    companion object
}

class MoodId(override val value: ID = ID.create()) : TypeSafeId() {
    constructor(id: String) : this(ID.of(id))
}
