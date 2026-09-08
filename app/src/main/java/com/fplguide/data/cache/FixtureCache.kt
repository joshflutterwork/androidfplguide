package com.fplguide.data.cache

import com.fplguide.domain.model.Fixture
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * In-memory cache for the whole-season fixture list. The payload is one flat list
 * (~380 rows covering all gameweeks), so a single entry is enough; the matchday
 * screen slices it by `event`.
 */
@Singleton
class FixtureCache @Inject constructor() {

    private val mutex = Mutex()

    private var fixtures: List<Fixture> = emptyList()
    private var cachedAtMillis: Long = 0

    suspend fun get(): List<Fixture>? = mutex.withLock {
        if (fixtures.isEmpty()) null else fixtures
    }

    suspend fun put(fixtures: List<Fixture>) = mutex.withLock {
        this.fixtures = fixtures
        this.cachedAtMillis = System.currentTimeMillis()
    }

    /** True when the cached snapshot is missing or older than [maxAgeMillis]. */
    suspend fun isStale(maxAgeMillis: Long = DEFAULT_MAX_AGE_MILLIS): Boolean = mutex.withLock {
        fixtures.isEmpty() || System.currentTimeMillis() - cachedAtMillis > maxAgeMillis
    }

    companion object {
        /** Fixtures move slowly (kickoff times rarely change); 1h matches bootstrap. */
        const val DEFAULT_MAX_AGE_MILLIS = 60 * 60 * 1000L
    }
}
