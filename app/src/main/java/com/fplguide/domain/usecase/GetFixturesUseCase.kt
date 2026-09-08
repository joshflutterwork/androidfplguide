package com.fplguide.domain.usecase

import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Fixture
import com.fplguide.domain.repository.FixtureRepository
import javax.inject.Inject

class GetFixturesUseCase @Inject constructor(
    private val repository: FixtureRepository,
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): AppResult<List<Fixture>> =
        repository.getFixtures(forceRefresh)
}
