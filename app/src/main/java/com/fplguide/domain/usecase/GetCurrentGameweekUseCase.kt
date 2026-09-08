package com.fplguide.domain.usecase

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Gameweek
import com.fplguide.domain.repository.PlayerRepository
import javax.inject.Inject

class GetCurrentGameweekUseCase @Inject constructor(
    private val repository: PlayerRepository,
) {
    suspend operator fun invoke(): AppResult<Gameweek?> = repository.getCurrentGameweek()
}
