package com.fplguide.data.repository

import com.fplguide.core.common.AppResult
import com.fplguide.core.common.DispatcherProvider
import com.fplguide.core.common.map
import com.fplguide.data.cache.FixtureCache
import com.fplguide.data.mapper.toFixtures
import com.fplguide.data.remote.api.FplApi
import com.fplguide.data.remote.apiCall
import com.fplguide.domain.model.Fixture
import com.fplguide.domain.repository.FixtureRepository
import com.fplguide.domain.repository.PlayerRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext

/**
 * Cache-first access to the whole-season fixture list. Teams for the join come from the
 * (already cached) bootstrap snapshot via [PlayerRepository], so opening the matchday tab
 * after the players tab never triggers a second bootstrap fetch.
 */
@Singleton
class FixtureRepositoryImpl @Inject constructor(
    private val api: FplApi,
    private val fixtureCache: FixtureCache,
    private val playerRepository: PlayerRepository,
    private val dispatchers: DispatcherProvider,
) : FixtureRepository {

    override suspend fun getFixtures(forceRefresh: Boolean): AppResult<List<Fixture>> =
        withContext(dispatchers.io) {
            if (!forceRefresh && !fixtureCache.isStale()) {
                fixtureCache.get()?.let { return@withContext AppResult.Success(it) }
            }

            val teamsById = when (val teams = playerRepository.getTeams()) {
                is AppResult.Success -> teams.data.associateBy { it.id }
                is AppResult.Failure -> return@withContext AppResult.Failure(teams.error)
            }

            when (val result = apiCall { api.getFixtures().toFixtures(teamsById) }) {
                is AppResult.Success -> {
                    fixtureCache.put(result.data)
                    result
                }
                is AppResult.Failure -> {
                    // Offline: serve the stale list over an error screen.
                    fixtureCache.get()?.let { AppResult.Success(it) } ?: result
                }
            }
        }
}
