package com.hero.feelin.domain.model

data class Feeling(
    val id: FeelingId = FeelingId()
) {
    companion object
}

class FeelingId(override val value: ID = ID.create()) : TypeSafeId() {
    constructor(id: String) : this(ID.of(id))
}
