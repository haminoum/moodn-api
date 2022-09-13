package com.hero.feelinapi.infrastructure.database

import com.hero.feelin.domain.model.FeelingId
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object FeelingTable : Table<Nothing>("feeling") {

    val id = int("id").primaryKey()
    val externalId = text("external_id").transform(fromUnderlyingValue = ::FeelingId, toUnderlyingValue = FeelingId::asString)
}
