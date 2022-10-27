package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.MoodId

interface Comments {

    fun create(comment: Comment)

    fun update(moodId: MoodId, comment: Comment)

    fun delete(comment: Comment, moodId: MoodId)
}
