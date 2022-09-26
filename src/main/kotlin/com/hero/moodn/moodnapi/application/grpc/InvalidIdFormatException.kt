package com.hero.moodn.moodnapi.application.grpc

class InvalidIdFormatException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
