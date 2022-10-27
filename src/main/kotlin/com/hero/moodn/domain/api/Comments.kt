package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId

interface Comments {

    fun create(comment: Comment)

    fun update(comment: Comment)

    fun delete(commentId: CommentId)
}
