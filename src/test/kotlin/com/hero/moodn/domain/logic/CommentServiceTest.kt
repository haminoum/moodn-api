package com.hero.moodn.domain.logic

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.spi.CommentRepository
import com.hero.moodn.domain.spi.MoodRepository
import fixture
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class CommentServiceTest {

    private lateinit var unit: CommentService
    private lateinit var moodRepository: MoodRepository
    private lateinit var commentRepository: CommentRepository

    @BeforeEach
    internal fun setUp() {
        moodRepository = mock()
        commentRepository = mock()

        unit = CommentService(moodRepository, commentRepository)

        mood = Mood.fixture()
    }

    @Test
    internal fun `createComment creates a comment`() {
        val mood = mood.copy()
        val comment = Comment.fixture().copy(mood = mood.id)

        whenever(moodRepository.find(any())).thenReturn(mood)

        unit.create(comment)

        verify(moodRepository).find(mood.id)
        verify(commentRepository).createOrUpdateComment(comment)
    }

    @Test
    internal fun `should update comment`() {
        val mood = mood.copy(comment = null)
        val updatedComment = Comment.fixture().copy(mood = mood.id)

        whenever(moodRepository.find(any())).thenReturn(mood)

        val commentCaptor = argumentCaptor<Comment>()

        unit.update(mood.id, updatedComment)

        verify(commentRepository).createOrUpdateComment(commentCaptor.capture())
        verify(moodRepository).find(mood.id)
        Assertions.assertThat(commentCaptor.firstValue).isEqualTo(updatedComment)
    }

    @Test
    internal fun `should delete comment`() {
        val comment = Comment.fixture().copy(mood = mood.id)
        val mood = mood.copy(comment = comment.id)

        whenever(moodRepository.find(any())).thenReturn(mood)

        val commentIdCaptor = argumentCaptor<CommentId>()

        unit.delete(comment, mood.id)

        verify(commentRepository).delete(commentIdCaptor.capture())
        verify(moodRepository).find(mood.id)
        Assertions.assertThat(commentIdCaptor.firstValue).isEqualTo(comment.id)
    }

    @Test
    internal fun `createComment throws an error if no mood found`() {
        assertThrows<NoSuchElementException> { unit.create(Comment.fixture()) }
    }

    @Test
    internal fun `update comment throws an error if no mood found`() {
        assertThrows<NoSuchElementException> { unit.update(MoodId(), Comment.fixture()) }
    }

    @Nested
    @DisplayName("Create")
    inner class Create {
        @Test
        internal fun `should create a comment`() {
            val mood = Mood.fixture()
            val comment = Comment.fixture().copy(mood = mood.id)

            whenever(moodRepository.find(any())).thenReturn(mood)

            unit.create(comment)

            verify(moodRepository).find(mood.id)
            verify(commentRepository).createOrUpdateComment(comment)
        }

        @Test
        internal fun `create throws an error if no mood found`() {
            assertThrows<NoSuchElementException> { unit.create(Comment.fixture()) }
        }
    }

    @Nested
    @DisplayName("Update")
    inner class Update {
        @Test
        internal fun `should update comment`() {
            val mood = Mood.fixture()
            val commentUpdate = Comment.fixture().copy(mood = mood.id)

            whenever(moodRepository.find(any())).thenReturn(mood)

            val commentCaptor = argumentCaptor<Comment>()

            unit.update(commentUpdate)

            verify(commentRepository).createOrUpdateComment(commentCaptor.capture())
            verify(moodRepository).find(mood.id)
            Assertions.assertThat(commentCaptor.firstValue).isEqualTo(commentUpdate)
        }

        @Test
        internal fun `update comment throws an error if no mood found`() {
            assertThrows<NoSuchElementException> { unit.update(Comment.fixture()) }
        }
    }

    @Nested
    @DisplayName("Delete")
    inner class Delete {
        @Test
        internal fun `should delete comment`() {
            val comment = Comment.fixture()

            val commentIdCaptor = argumentCaptor<CommentId>()
            unit.delete(comment.id)

            verify(commentRepository).delete(commentIdCaptor.capture())
        }
    }
}
