package com.hero.moodn.moodnapi.application.grpc

import io.grpc.Status
import io.grpc.StatusException
import io.grpc.StatusRuntimeException
import mu.KotlinLogging.logger
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import javax.validation.ConstraintViolationException

class GrpcExceptionHandling {

    private val log = logger {}

    @GrpcExceptionHandler(Throwable::class)
    fun handleException(e: Throwable): Status {
        log.error(e) { "Caught unexpected exception in gRPC Handler" }
        return Status.UNKNOWN.withCause(e)
    }

    @GrpcExceptionHandler(StatusRuntimeException::class)
    fun handleException(e: StatusRuntimeException): StatusRuntimeException {
        return e
    }

    @GrpcExceptionHandler(StatusException::class)
    fun handleException(e: StatusException): StatusException {
        return e
    }

    @GrpcExceptionHandler(NoSuchElementException::class)
    fun handleException(e: NoSuchElementException): Status =
        Status.NOT_FOUND.withCause(e).withDescription(e.message)

    @GrpcExceptionHandler(InvalidIdFormatException::class)
    fun handleException(e: InvalidIdFormatException): Status =
        Status.INVALID_ARGUMENT.withCause(e).withDescription(e.message)

    @GrpcExceptionHandler(ConstraintViolationException::class)
    fun handleException(e: ConstraintViolationException): Status =
        Status.INVALID_ARGUMENT.withCause(e).withDescription(e.message)
}
