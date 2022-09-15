package com.hero.moodin.domain.logic

import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.User
import com.hero.feelin.domain.model.UserId
import com.hero.moodin.domain.spi.MoodSPI
import com.hero.moodin.domain.spi.UserSPI
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

    lateinit var unit: MoodService
    lateinit var moodSPI: MoodSPI
    lateinit var userSPI: UserSPI

    @BeforeEach
    fun setUp() {
        moodSPI = mock()
        userSPI = mock()

        unit = MoodService(moodSPI, userSPI)
    }

    @Test
    internal fun `should create a mood`() {
        val mood = Mood.fixture()
        val user = User.fixture()

        val moodCaptor = argumentCaptor<Mood>()
        val userIdCaptor = argumentCaptor<UserId>()

        whenever(userSPI.find(user.username)).thenReturn(user)

        unit.create(mood, user.username)

        verify(userSPI, times(1)).find(user.username)
        verify(moodSPI, times(1)).create(moodCaptor.capture(), userIdCaptor.capture())
        assertThat(moodCaptor.firstValue).isEqualTo(mood)
        assertThat(userIdCaptor.firstValue).isEqualTo(user.id)
    }

    @Test
    internal fun `shouldn't create mood when user doesn't exist`() {
        val mood = Mood.fixture()
        assertThrows<IllegalArgumentException> { unit.create(mood, "Rougue") }
    }
}
