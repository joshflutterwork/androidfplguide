package com.fplguide.data.remote

import com.fplguide.core.common.AppError
import com.fplguide.core.common.AppResult
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

/**
 * Single place where transport exceptions become [AppError]s. Shared by every repository
 * impl so exception mapping can never drift between endpoints.
 */
internal inline fun <T> apiCall(block: () -> T): AppResult<T> = try {
    AppResult.Success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: IOException) {
    AppResult.Failure(AppError.Network)
} catch (e: HttpException) {
    AppResult.Failure(AppError.Server(e.code()))
} catch (e: SerializationException) {
    AppResult.Failure(AppError.Serialization)
} catch (e: Exception) {
    AppResult.Failure(AppError.Unknown(e.message))
}
