package com.hero.moodn.domain.model

import java.time.Instant

data class Comment(
    val id: CommentId = CommentId(),
    val content: String,
    val mood: MoodId,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
) {
    companion object
}

class CommentId(override val value: ID = ID.create()) : TypeSafeId() {
    constructor(id: String) : this(ID.of(id))
}
