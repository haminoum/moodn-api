package com.hero.moodin.domain.logic

import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodId
import com.hero.feelin.domain.model.MoodType
import com.hero.moodin.domain.spi.MoodSPI
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

internal class MoodServiceTest {

    lateinit var unit: FeelingService
    lateinit var moodSPI: MoodSPI

    @BeforeEach
    fun setUp() {
        moodSPI = mock()
        unit = FeelingService(moodSPI)
    }

    @Test
    internal fun `should create a feeling`() {
        val mood = Mood(MoodId(), type = MoodType.HAPPY)

        val captor = argumentCaptor<Mood>()

        unit.create(mood)

        verify(moodSPI, times(1)).create(captor.capture())
        assertThat(captor.firstValue).isEqualTo(mood)
    }
}
