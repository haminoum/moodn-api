package com.hero.feelin.domain.model

import com.aventrix.jnanoid.jnanoid.NanoIdUtils.randomNanoId
import java.security.SecureRandom
import java.util.Random

class ID private constructor(value: String) {

    companion object {
        private val alphabet: String = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private val random = ThreadLocal.withInitial { SecureRandom() }
        private val characters = alphabet.toCharArray()
        private const val length = 26

        @JvmStatic
        fun create(random: Random = Companion.random.get()): ID {
            return of(randomNanoId(random, characters, length))
        }

        @JvmStatic
        fun of(value: String) = ID(validate(value))

        private fun validate(value: String): String {
            require(value.length == length) { "Incorrect length (expected $length)" }
            require(value.all(characters::contains)) { "Incorrect format/alphabet (expected $alphabet)" }
            return value
        }
    }

    val value: String = value

    override operator fun equals(other: Any?): Boolean {
        return this.value == of(other.toString()).value
    }

    override fun hashCode(): Int = this.hashCode()

    override fun toString(): String = this.value
}
