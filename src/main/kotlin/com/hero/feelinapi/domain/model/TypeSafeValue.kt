package com.hero.feelin.domain.model

abstract class TypeSafeString : TypeSafeValue<String>()

abstract class TypeSafeId : TypeSafeValue<ID>()

abstract class TypeSafeValue<T> {
    abstract val value: T

    val asString: String
        get() = value.toString()

    /**
     * Usage of toString() on a null instance of TypeSafeValue will return `"null"`,
     * which will most likely lead to bugs.
     * Use the alternative asString method, which forces us to handle null values explicitly.
     */
    @Deprecated("Use the alternative asString method", ReplaceWith("asString"))
    override fun toString() = value.toString()

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            javaClass == other?.javaClass -> value == (other as TypeSafeValue<*>).value
            else -> false
        }
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
