package com.fplguide.data.cache

import com.fplguide.data.mapper.BootstrapSnapshot
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Process-lifetime cache of the mapped `bootstrap-static` snapshot — domain models only,
 * never DTOs, so an API schema change can't poison the cached shape.
 *
 * The FPL bootstrap payload changes at most daily, so a one-hour TTL is generous; callers
 * may keep serving the stale snapshot (offline) and let `forceRefresh` bypass everything.
 */
@Singleton
class BootstrapCache @Inject constructor() {

    private val mutex = Mutex()

    private var snapshot: BootstrapSnapshot? = null
    private var cachedAtMillis: Long = 0

    suspend fun get(): BootstrapSnapshot? = mutex.withLock { snapshot }

    /** Non-suspending read for the stale-on-error fallback path. */
    fun peek(): BootstrapSnapshot? = snapshot

    suspend fun put(value: BootstrapSnapshot) = mutex.withLock {
        snapshot = value
        cachedAtMillis = System.currentTimeMillis()
    }

    fun isFresh(nowMillis: Long = System.currentTimeMillis()): Boolean =
        snapshot != null && nowMillis - cachedAtMillis < TTL_MILLIS

    private companion object {
        const val TTL_MILLIS = 60 * 60 * 1000L // 1 hour
    }
}
