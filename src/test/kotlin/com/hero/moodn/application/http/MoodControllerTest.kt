package com.hero.moodn.application.http

import com.hero.moodn.domain.api.Moods
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.User
import fixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class MoodControllerTest {

    lateinit var unit: MoodController

    lateinit var moods: Moods

    @BeforeEach
    internal fun setUp() {
        moods = mock()
        unit = MoodController(moods)
    }

    @Test
    internal fun `POST should add a new mood via `() {
        val mood = Mood.fixture()
        val user = User.fixture().copy(username = "Lil Ham")

        unit.addMood(mood.type.name, user.username)

        verify(moods).create(any(), any())
    }
}
