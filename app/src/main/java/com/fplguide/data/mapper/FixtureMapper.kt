package com.fplguide.data.mapper

import com.fplguide.data.remote.dto.FixtureDto
import com.fplguide.domain.model.Fixture
import com.fplguide.domain.model.Team

/**
 * Joins fixture rows with teams from the bootstrap snapshot. A missing team degrades to a
 * placeholder (same convention as [BootstrapMapper]) instead of dropping the fixture.
 */
fun List<FixtureDto>.toFixtures(teamsById: Map<Int, Team>): List<Fixture> = map { dto ->
    Fixture(
        id = dto.id,
        event = dto.event,
        home = teamsById[dto.teamHome] ?: Team(dto.teamHome, 0, "Unknown", "UNK"),
        away = teamsById[dto.teamAway] ?: Team(dto.teamAway, 0, "Unknown", "UNK"),
        homeDifficulty = dto.teamHomeDifficulty,
        awayDifficulty = dto.teamAwayDifficulty,
        homeScore = dto.teamHomeScore,
        awayScore = dto.teamAwayScore,
        kickoffTime = dto.kickoffTime,
        started = dto.started,
        finished = dto.finished,
        minutes = dto.minutes,
    )
}
