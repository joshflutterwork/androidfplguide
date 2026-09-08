package com.fplguide.domain.repository

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Fixture

interface FixtureRepository {

    /**
     * Every fixture of the current season, joined with teams. One payload covers all
     * gameweeks, so the matchday screen filters by `event` client-side.
     *
     * @param forceRefresh bypasses the cache (pull-to-refresh).
     */
    suspend fun getFixtures(forceRefresh: Boolean = false): AppResult<List<Fixture>>
}
