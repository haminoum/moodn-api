package com.hero.feelinapi.domain.logic

import com.hero.feelin.domain.model.Feeling
import com.hero.feelin.domain.model.FeelingId
import com.hero.feelinapi.domain.spi.FeelingSPI
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

internal class FeelingServiceTest {

    lateinit var unit: FeelingService
    lateinit var feelingSPI: FeelingSPI

    @BeforeEach
    fun setUp() {
        feelingSPI = mock()
        unit = FeelingService(feelingSPI)
    }

    @Test
    internal fun `should create a feeling`() {
        val feeling = Feeling(FeelingId())

        val captor = argumentCaptor<Feeling>()

        unit.createFeeling(feeling)

        verify(feelingSPI, times(1)).create(captor.capture())
        assertThat(captor.firstValue).isEqualTo(feeling)
    }
}
