package com.fplguide.core.common

/**
 * Result wrapper used across layer boundaries so that the domain and presentation layers
 * never have to know about Retrofit/OkHttp exception types.
 */
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure(val error: AppError) : AppResult<Nothing>
}

sealed interface AppError {
    /** No connectivity, DNS failure, socket timeout. */
    data object Network : AppError

    /** Server answered with a non-2xx status. */
    data class Server(val code: Int) : AppError

    /** Payload could not be parsed — usually an FPL schema change. */
    data object Serialization : AppError

    data class Unknown(val message: String?) : AppError
}

inline fun <T, R> AppResult<T>.map(transform: (T) -> R): AppResult<R> = when (this) {
    is AppResult.Success -> AppResult.Success(transform(data))
    is AppResult.Failure -> this
}

fun <T> AppResult<T>.getOrNull(): T? = (this as? AppResult.Success)?.data
