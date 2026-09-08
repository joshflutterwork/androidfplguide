package com.fplguide.domain.usecase

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Team
import com.fplguide.domain.repository.PlayerRepository
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): AppResult<List<Team>> =
        repository.getTeams(forceRefresh)
}
