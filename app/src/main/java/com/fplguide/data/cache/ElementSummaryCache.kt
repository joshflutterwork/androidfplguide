package com.fplguide.data.cache

import com.fplguide.domain.model.PlayerDetail
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Memory-only cache of `element-summary` responses, keyed by player id. Small payloads,
 * low reuse value across process restarts — a disk snapshot would buy nothing.
 */
@Singleton
class ElementSummaryCache @Inject constructor() {

    private val mutex = Mutex()
    private val details = HashMap<Int, PlayerDetail>()

    suspend fun get(playerId: Int): PlayerDetail? = mutex.withLock { details[playerId] }

    suspend fun put(playerId: Int, detail: PlayerDetail) = mutex.withLock {
        details[playerId] = detail
    }
}
