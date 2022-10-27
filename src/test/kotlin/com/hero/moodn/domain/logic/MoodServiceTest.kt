package com.hero.moodn.domain.logic

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.MoodType.HAPPY
import com.hero.moodn.domain.model.MoodType.SAD
import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.domain.spi.UserRepository
import fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.times
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@Tag("unit-test")
internal class MoodServiceTest {

    private lateinit var unit: MoodService
    private lateinit var moodRepository: MoodRepository
    private lateinit var userRepository: UserRepository
    private lateinit var user: User
    private lateinit var mood: Mood

    @BeforeEach
    internal fun setUp() {
        moodRepository = mock()
        userRepository = mock()

        unit = MoodService(moodRepository, userRepository)

        user = User.fixture()
        mood = Mood.fixture()
    }

    @Nested
    @DisplayName("Valid")
    inner class Valid {

        @Nested
        @DisplayName("Create")
        inner class Create {
            @Test
            internal fun `should create mood`() {
                val mood = mood
                whenever(userRepository.find(user.id)).thenReturn(user)

                val moodCaptor = argumentCaptor<Mood>()
                val userIdCaptor = argumentCaptor<UserId>()

                unit.create(mood, user.id)

                verify(userRepository).find(user.id)
                verify(moodRepository).createAll(listOf(moodCaptor.capture()), userIdCaptor.capture())
                assertThat(moodCaptor.firstValue).isEqualTo(listOf(mood))
                assertThat(userIdCaptor.firstValue).isEqualTo(user.id)
            }

            @Test
            internal fun `should create list of moods`() {
                val mood = mood
                val anotherMood = mood.copy(id = MoodId())
                val moods = listOf(mood, anotherMood)
                whenever(userRepository.find(user.id)).thenReturn(user)

                val moodCaptor = argumentCaptor<Mood>()
                val userIdCaptor = argumentCaptor<UserId>()
                unit.createAll(moods, user.id)

                verify(userRepository, times(1)).find(user.id)
                verify(moodRepository, times(1)).createAll(listOf(moodCaptor.capture()), userIdCaptor.capture())
                assertThat(moodCaptor.firstValue).isEqualTo(moods)
                assertThat(userIdCaptor.firstValue).isEqualTo(user.id)
            }

            @Test
            internal fun `createComment creates a comment`() {
                val mood = mood.copy()
                val comment = Comment.fixture().copy(mood = mood.id)

                whenever(moodRepository.find(any())).thenReturn(mood)

                unit.createComment(comment)

                verify(moodRepository).find(mood.id)
                verify(moodRepository).createOrUpdateComment(comment)
            }
        }

        @Nested
        @DisplayName("Update")
        inner class Update {
            @Test
            internal fun `should update mood`() {
                val user = user
                val mood = mood.copy(type = SAD, user = user.id)
                val updatedMood = mood.copy(type = HAPPY)

                whenever(moodRepository.find(mood.id)).thenReturn(mood)
                whenever(userRepository.find(any())).thenReturn(user)

                val idCaptor = argumentCaptor<MoodId>()
                val typeCaptor = argumentCaptor<MoodType>()

                unit.updateType(updatedMood, user.id)

                verify(moodRepository, times(1)).updateType(idCaptor.capture(), typeCaptor.capture())
                assertThat(idCaptor.firstValue).isEqualTo(mood.id)
                assertThat(typeCaptor.firstValue).isEqualTo(updatedMood.type)
            }

            @Test
            internal fun `should update comment`() {
                val mood = mood.copy(comment = null)
                val updatedComment = Comment.fixture().copy(mood = mood.id)

                whenever(moodRepository.find(any())).thenReturn(mood)

                val commentCaptor = argumentCaptor<Comment>()

                unit.updateComment(mood.id, updatedComment)

                verify(moodRepository).createOrUpdateComment(commentCaptor.capture())
                verify(moodRepository).find(mood.id)
                assertThat(commentCaptor.firstValue).isEqualTo(updatedComment)
            }
        }

        @Nested
        @DisplayName("Delete")
        inner class Delete {
            @Test
            internal fun `should delete mood`() {
                val user = user.copy()
                val mood = mood.copy(type = SAD, user = user.id)

                whenever(userRepository.find(any())).thenReturn(user)
                whenever(moodRepository.find(mood.id)).thenReturn(mood)

                val idCaptor = argumentCaptor<MoodId>()

                unit.delete(mood.id, user.id)

                verify(moodRepository, times(1)).delete(idCaptor.capture())
            }

            @Test
            internal fun `should delete comment`() {
                val comment = Comment.fixture().copy(mood = mood.id)
                val mood = mood.copy(comment = comment.id)

                whenever(moodRepository.find(any())).thenReturn(mood)

                val commentIdCaptor = argumentCaptor<CommentId>()

                unit.deleteComment(comment, mood.id)

                verify(moodRepository).deleteComment(commentIdCaptor.capture())
                verify(moodRepository).find(mood.id)
                assertThat(commentIdCaptor.firstValue).isEqualTo(comment.id)
            }
        }
    }

    @Nested
    @DisplayName("Invalid")
    inner class Invalid {

        @Nested
        @DisplayName("Create")
        inner class Create {

            @Test
            internal fun `create should throw an error if no user found`() {
                assertThrows<NoSuchElementException> { unit.create(Mood.fixture(), UserId()) }
                verifyNoInteractions(moodRepository)
            }

            @Test
            internal fun `createAll should throw an error`() {
                assertThrows<NoSuchElementException> { unit.createAll(emptyList(), UserId()) }
                verifyNoInteractions(moodRepository)
            }

            @Test
            internal fun `createComment throws an error if no mood found`() {
                whenever(userRepository.find(any())).thenReturn(user)
                assertThrows<NoSuchElementException> { unit.createComment(Comment.fixture()) }
            }
        }

        @Nested
        @DisplayName("Update")
        inner class Update {
            @Test
            internal fun `update throws an error if no mood found`() {
                whenever(userRepository.find(any())).thenReturn(user)
                assertThrows<NoSuchElementException> { unit.updateType(Mood.fixture(), UserId()) }
                verifyNoInteractions(userRepository)
            }

            @Test
            internal fun `update comment throws an error if no mood found`() {
                whenever(userRepository.find(any())).thenReturn(user)
                assertThrows<NoSuchElementException> { unit.updateComment(MoodId(), Comment.fixture()) }
                verifyNoInteractions(userRepository)
            }
        }

        @Nested
        @DisplayName("Delete")
        inner class Delete
    }
}
