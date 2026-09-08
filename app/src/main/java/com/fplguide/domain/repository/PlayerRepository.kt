package com.fplguide.domain.repository

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Gameweek
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerDetail
import com.fplguide.domain.model.Team

interface PlayerRepository {
    /**
     * All players for the current season, already joined with their team.
     *
     * @param forceRefresh bypasses the cached bootstrap snapshot (pull-to-refresh).
     */
    suspend fun getPlayers(forceRefresh: Boolean = false): AppResult<List<Player>>

    suspend fun getTeams(forceRefresh: Boolean = false): AppResult<List<Team>>

    suspend fun getCurrentGameweek(): AppResult<Gameweek?>

    suspend fun getPlayerDetail(playerId: Int): AppResult<PlayerDetail>
}
