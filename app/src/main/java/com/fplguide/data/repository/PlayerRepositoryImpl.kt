package com.fplguide.data.repository

import com.fplguide.core.common.AppError
import com.fplguide.core.common.AppResult
import com.fplguide.core.common.DispatcherProvider
import com.fplguide.core.common.map
import com.fplguide.data.cache.BootstrapCache
import com.fplguide.data.cache.ElementSummaryCache
import com.fplguide.data.mapper.BootstrapSnapshot
import com.fplguide.data.mapper.toDomain
import com.fplguide.data.remote.api.FplApi
import com.fplguide.data.remote.apiCall
import com.fplguide.domain.model.Gameweek
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerDetail
import com.fplguide.domain.model.Team
import com.fplguide.domain.repository.PlayerRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext

/**
 * Cache-first orchestration of the two public FPL endpoints.
 *
 * This is the ONLY layer permitted to catch IO exceptions — everything leaving here is an
 * [AppResult], so the domain and UI stay blind to Retrofit/OkHttp types. Offline behaviour:
 * a failed refresh falls back to the stale snapshot when one exists.
 */
@Singleton
class PlayerRepositoryImpl @Inject constructor(
    private val api: FplApi,
    private val bootstrapCache: BootstrapCache,
    private val elementSummaryCache: ElementSummaryCache,
    private val dispatchers: DispatcherProvider,
) : PlayerRepository {

    override suspend fun getPlayers(forceRefresh: Boolean): AppResult<List<Player>> =
        withContext(dispatchers.io) {
            snapshot(forceRefresh).map { it.players }
        }

    override suspend fun getTeams(forceRefresh: Boolean): AppResult<List<Team>> =
        withContext(dispatchers.io) {
            snapshot(forceRefresh).map { it.teams }
        }

    override suspend fun getCurrentGameweek(): AppResult<Gameweek?> =
        withContext(dispatchers.io) {
            snapshot(forceRefresh = false).map { snap ->
                snap.gameweeks.firstOrNull { it.isCurrent }
                    ?: snap.gameweeks.firstOrNull { it.isNext }
            }
        }

    override suspend fun getPlayerDetail(playerId: Int): AppResult<PlayerDetail> =
        withContext(dispatchers.io) {
            elementSummaryCache.get(playerId)?.let { return@withContext AppResult.Success(it) }

            val snap = when (val result = snapshot(forceRefresh = false)) {
                is AppResult.Success -> result.data
                is AppResult.Failure -> return@withContext AppResult.Failure(result.error)
            }
            val player = snap.players.firstOrNull { it.id == playerId }
                ?: return@withContext AppResult.Failure(AppError.Server(404))

            val teamsById = snap.teams.associateBy { it.id }
            when (val detail = apiCall { api.getElementSummary(playerId).toDomain(player, teamsById) }) {
                is AppResult.Success -> {
                    elementSummaryCache.put(playerId, detail.data)
                    detail
                }
                is AppResult.Failure -> detail
            }
        }

    /**
     * Fresh cache hit short-circuits; otherwise fetch, map, cache. On fetch failure we
     * still prefer a stale snapshot over an error screen whenever data exists.
     */
    private suspend fun snapshot(forceRefresh: Boolean): AppResult<BootstrapSnapshot> {
        if (!forceRefresh && bootstrapCache.isFresh()) {
            bootstrapCache.get()?.let { return AppResult.Success(it) }
        }

        return when (val result = apiCall { api.getBootstrap().toDomain() }) {
            is AppResult.Success -> {
                bootstrapCache.put(result.data)
                result
            }
            is AppResult.Failure -> {
                val stale = bootstrapCache.peek()
                if (stale != null) AppResult.Success(stale) else result
            }
        }
    }

}
