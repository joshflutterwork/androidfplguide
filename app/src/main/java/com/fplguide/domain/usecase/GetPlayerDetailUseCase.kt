package com.fplguide.domain.usecase

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.PlayerDetail
import com.fplguide.domain.repository.PlayerRepository
import javax.inject.Inject

class GetPlayerDetailUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {
    suspend operator fun invoke(playerId: Int): AppResult<PlayerDetail> =
        repository.getPlayerDetail(playerId)
}
