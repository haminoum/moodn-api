package com.hero.moodn.domain.logic

import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.UserRepository
import fixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@Tag("unit-test")
internal class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var unit: UserService

    @BeforeEach
    internal fun setUp() {
        userRepository = mock()
        unit = UserService(userRepository)
    }

    @Nested
    @DisplayName("User found")
    inner class UserFound {
        private val user = User.fixture()

        @Test
        internal fun `should create a new user`() {
            unit.create(user)
            verify(userRepository).create(user)
        }

        @Test
        internal fun `should find user`() {
            whenever(userRepository.find(user.id)).thenReturn(user)

            unit.find(user.id)

            verify(userRepository).find(user.id)
        }
    }

    @Nested
    @DisplayName("User not found")
    inner class UserNotFound {
        @Test
        internal fun `should throw error if no user found`() {
            assertThrows<NoSuchElementException> { unit.find(UserId()) }
            verify(userRepository).find(any())
        }
    }
}
