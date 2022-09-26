package com.hero.moodn.moodnapi.application.grpc // ktlint-disable filename


import com.hero.moodn.moodnapi.domain.model.ID
import com.hero.moodn.moodnapi.domain.model.TypeSafeId
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import kotlin.reflect.KFunction
import kotlin.reflect.typeOf

fun <T> singleResponse(
    responseObserver: StreamObserver<T>,
    block: () -> T,
) {
    responseObserver.onNext(block())
    responseObserver.onCompleted()
}

fun parseId(id: String) = try {
    ID.of(id)
} catch (e: IllegalArgumentException) {
    throw InvalidIdFormatException("Could not parse id:'$id'.", e)
}

inline fun <reified T : TypeSafeId> requireId(supplier: KFunction<String>, prefix: String = ""): T {
    return findIdConstructor<T>().call(parseId(requireString(supplier, prefix)))
}

inline fun <reified T : TypeSafeId> optionalId(supplier: KFunction<String>): T? {
    return supplier.call().takeIf { it.isNotBlank() }?.let { findIdConstructor<T>().call(parseId(it)) }
}

inline fun <reified T : TypeSafeId> findIdConstructor() =
    T::class.constructors.find { it.parameters.singleOrNull()?.type == typeOf<ID>() }!!

fun requireString(supplier: KFunction<String>, customPrefix: String = ""): String =
    supplier.call()
        .takeIf { it.isNotBlank() }
        ?: throw StatusRuntimeException(
            Status.INVALID_ARGUMENT.withDescription("${customPrefix}${supplier.name.removePrefix("get")} must not be blank"),
        )

fun String.trimToNull(): String? = this.trim().takeUnless { it == "" }

fun String?.nullToBlank(): String = this ?: ""

fun String.requireMinLength(min: Int): String = when {
    length >= min -> this
    else -> throw StatusRuntimeException(
        Status.INVALID_ARGUMENT.withDescription("$this} must have at least a length of $min characters"),
    )
}
