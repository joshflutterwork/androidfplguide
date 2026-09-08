package com.fplguide.domain.usecase

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Player
import com.fplguide.domain.repository.PlayerRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): AppResult<List<Player>> =
        repository.getPlayers(forceRefresh)
}
