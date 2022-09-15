package com.hero.feelin.domain.model

data class User(
    val id: UserId = UserId(),
    val username: String
) {
    companion object
}

class UserId(override val value: String) : TypeSafeString() {
    constructor() : this(ID.create().toString())
}

// TODO create userInfo
//created_at  timestamptz not null,
