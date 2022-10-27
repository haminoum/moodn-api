package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository {

    fun createOrUpdateComment(comment: Comment): Comment?

    fun find(commentId: CommentId): Comment?

    fun delete(commentId: CommentId): Boolean
}
