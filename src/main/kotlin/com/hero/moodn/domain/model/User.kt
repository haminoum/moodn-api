package com.hero.moodn.domain.model

import com.hero.feelin.domain.model.ID
import com.hero.feelin.domain.model.TypeSafeString
import java.time.Instant

data class User(
    val id: UserId = UserId(),
    val username: String,
    val createdAt: Instant = Instant.now(),
) {
    companion object
}

class UserId(override val value: String) : TypeSafeString() {
    constructor() : this(ID.create().toString())
}
