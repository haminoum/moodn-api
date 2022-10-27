package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId

interface Users {
    fun create(user: User)

    fun find(userId: UserId): User?
}
