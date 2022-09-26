package com.hero.moodn.moodnapi.infrastructure.database // ktlint-disable filename

import org.ktorm.database.Database
import org.ktorm.dsl.deleteAll

fun cleanAllTables(database: Database) {
    database.deleteAll(UserTable)
    database.deleteAll(MoodTable)
}
