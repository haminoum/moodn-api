package com.hero.moodn.domain.logic

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.domain.spi.UserRepository
import fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class MoodServiceTest {

    private lateinit var unit: MoodService
    lateinit var moodRepository: MoodRepository
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        moodRepository = mock()
        userRepository = mock()

        unit = MoodService(moodRepository, userRepository)
    }

    @Test
    internal fun `should create a mood`() {
        val mood = Mood.fixture()
        val user = User.fixture()

        val moodCaptor = argumentCaptor<Mood>()
        val userIdCaptor = argumentCaptor<UserId>()

        whenever(userRepository.find(user.username)).thenReturn(user)

        unit.create(mood, user.username)

        verify(userRepository, times(1)).find(user.username)
        verify(moodRepository, times(1)).create(moodCaptor.capture(), userIdCaptor.capture())
        assertThat(moodCaptor.firstValue).isEqualTo(mood)
        assertThat(userIdCaptor.firstValue).isEqualTo(user.id)
    }

    @Test
    internal fun `shouldn't create mood when user doesn't exist`() {
        val mood = Mood.fixture()
        assertThrows<IllegalArgumentException> { unit.create(mood, "Rougue") }
    }

    @Test
    internal fun `should create multiple moods for a user`() {
        val moods = listOf(
            Mood(id = MoodId(), type = MoodType.HAPPY),
            Mood(id = MoodId(), type = MoodType.NEUTRAL),
            Mood(id = MoodId(), type = MoodType.SAD)
        )
        val user = User.fixture()

        val moodsCaptor = argumentCaptor<Set<Mood>>()
        val usernameCaptor = argumentCaptor<String>()

        unit.createAll(moods, user.username)

        verify(userRepository, times(1)).find(user.username)
        verify(moodRepository, times(1)).createAll(moods, user.id)
        assertThat(moodsCaptor.firstValue).isEqualTo(moods)
        assertThat(usernameCaptor.firstValue).isEqualTo(user.username)
    }
}
