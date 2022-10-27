package com.hero.moodn.domain.logic

import com.hero.moodn.domain.model.Comment
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
    @DisplayName("Mood found")
    inner class MoodFound {
        @Test
        internal fun `createComment updates mood`() {
            val user = user
            val mood = mood.copy(user = user.id)
            val comment = Comment.fixture().copy(author = user.id)

            whenever(moodRepository.find(any())).thenReturn(mood)
            whenever(userRepository.find(any())).thenReturn(user)

            unit.createComment(mood.id, comment)

            verify(moodRepository).find(mood.id)
            verify(userRepository).find(user.id)
            verify(moodRepository).update(mood.id, comment)
        }

        @Test
        internal fun `should update mood`() {
            val mood = mood.copy(type = SAD)
            val updatedMoodType = HAPPY

            whenever(moodRepository.find(mood.id)).thenReturn(mood)

            val idCaptor = argumentCaptor<MoodId>()
            val typeCaptor = argumentCaptor<MoodType>()

            unit.update(mood.id, updatedMoodType)

            verify(moodRepository, times(1)).update(idCaptor.capture(), typeCaptor.capture())
            verifyNoInteractions(userRepository)
            assertThat(idCaptor.firstValue).isEqualTo(mood.id)
            assertThat(typeCaptor.firstValue).isEqualTo(updatedMoodType)
        }
    }

    @Nested
    @DisplayName("User found")
    inner class UserFound {

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
        internal fun `createComment throws an error if user is different`() {
            val user = user
            val anotherUser = User.fixture().copy(id = UserId())

            val mood = mood.copy(user = user.id)
            val comment = Comment.fixture().copy(author = anotherUser.id)

            whenever(moodRepository.find(mood.id)).thenReturn(mood)
            whenever(userRepository.find(comment.author)).thenReturn(anotherUser)

            assertThrows<IllegalArgumentException> { unit.createComment(mood.id, comment) }

            verify(moodRepository).find(any())
            verify(userRepository).find(any())
        }
    }

    @Nested
    @DisplayName("User not found")
    inner class UserNotFound {

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
        internal fun `createComment throws an error`() {
            val mood = mood
            whenever(moodRepository.find(mood.id)).thenReturn(mood)

            assertThrows<IllegalArgumentException> { unit.createComment(mood.id, Comment.fixture()) }

            verify(moodRepository, times(1)).find(mood.id)
        }
    }

    @Nested
    @DisplayName("Mood not found")
    inner class MoodNotFound {
        @Test
        internal fun `createComment throws an error if no mood found`() {
            assertThrows<NoSuchElementException> { unit.createComment(MoodId(), Comment.fixture()) }
            verifyNoInteractions(userRepository)
        }

        @Test
        internal fun `update throws an error if no mood found`() {
            assertThrows<NoSuchElementException> { unit.update(MoodId(), HAPPY) }
            verifyNoInteractions(userRepository)
        }
    }
}
