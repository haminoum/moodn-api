package com.hero.moodn.domain.logic

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
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

    @BeforeEach
    internal fun setUp() {
        moodRepository = mock()
        userRepository = mock()
        unit = MoodService(moodRepository, userRepository)
    }

    @Nested
    @DisplayName("User found")
    inner class UserFound {
        private val user = User.fixture()

        @Test
        internal fun `should create mood`() {
            val mood = Mood.fixture()
            whenever(userRepository.find(user.id)).thenReturn(user)

            val moodCaptor = argumentCaptor<Mood>()
            val userIdCaptor = argumentCaptor<UserId>()

            unit.add(mood, user.id)

            verify(userRepository).find(user.id)
            verify(moodRepository).createAll(listOf(moodCaptor.capture()), userIdCaptor.capture())
            assertThat(moodCaptor.firstValue).isEqualTo(listOf(mood))
            assertThat(userIdCaptor.firstValue).isEqualTo(user.id)
        }

        @Test
        internal fun `should create list of moods`() {
            val mood = Mood.fixture()
            val anotherMood = mood.copy(id = MoodId())
            val moods = listOf(mood, anotherMood)
            whenever(userRepository.find(user.id)).thenReturn(user)

            val moodCaptor = argumentCaptor<Mood>()
            val userIdCaptor = argumentCaptor<UserId>()
            unit.addAll(moods, user.id)

            verify(userRepository, times(1)).find(user.id)
            verify(moodRepository, times(1)).createAll(listOf(moodCaptor.capture()), userIdCaptor.capture())
            assertThat(moodCaptor.firstValue).isEqualTo(moods)
            assertThat(userIdCaptor.firstValue).isEqualTo(user.id)
        }
    }

    @Nested
    @DisplayName("User not found")
    inner class UserNotFound {

        @Test
        internal fun `create should throw an error if no user found`() {
            assertThrows<NoSuchElementException> { unit.add(Mood.fixture(), UserId()) }
            verifyNoInteractions(moodRepository)
        }

        @Test
        internal fun `createAll should throw an error if no user found`() {
            assertThrows<NoSuchElementException> { unit.addAll(emptyList(), UserId()) }
            verifyNoInteractions(moodRepository)
        }
    }
}
