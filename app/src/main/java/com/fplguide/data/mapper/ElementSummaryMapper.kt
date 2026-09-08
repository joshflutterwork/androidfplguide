package com.fplguide.data.mapper

import com.fplguide.core.util.toDoubleOrZero
import com.fplguide.data.remote.dto.ElementSummaryDto
import com.fplguide.data.remote.dto.SummaryFixtureDto
import com.fplguide.data.remote.dto.SummaryHistoryDto
import com.fplguide.data.remote.dto.SummaryPastDto
import com.fplguide.domain.model.GameweekHistory
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerDetail
import com.fplguide.domain.model.SeasonHistory
import com.fplguide.domain.model.Team
import com.fplguide.domain.model.UpcomingFixture

/** How many upcoming fixtures the detail screen renders. */
private const val UPCOMING_FIXTURE_COUNT = 5

/**
 * Joins an `element-summary` payload with its parent [Player] and the league's teams.
 *
 * Fixture opponents are derived from `team_h`/`team_a` + `is_home` — the API never names
 * the opponent directly. A missing team id degrades to `null` rather than crashing;
 * the UI treats a null opponent as "TBC".
 */
fun ElementSummaryDto.toDomain(
    player: Player,
    teamsById: Map<Int, Team>,
): PlayerDetail = PlayerDetail(
    player = player,
    upcomingFixtures = fixtures
        .filter { !it.finished }
        .take(UPCOMING_FIXTURE_COUNT)
        .map { it.toUpcomingFixture(teamsById) },
    history = history.map { it.toDomain(teamsById) },
    pastSeasons = historyPast.map { it.toDomain() },
)

private fun SummaryFixtureDto.toUpcomingFixture(teamsById: Map<Int, Team>): UpcomingFixture {
    val opponentId = if (isHome) teamAway else teamHome
    return UpcomingFixture(
        eventId = event,
        eventName = eventName,
        opponent = teamsById[opponentId],
        isHome = isHome,
        difficulty = difficulty,
        kickoffTime = kickoffTime,
    )
}

private fun SummaryHistoryDto.toDomain(teamsById: Map<Int, Team>): GameweekHistory =
    GameweekHistory(
        round = round,
        opponent = teamsById[opponentTeam],
        wasHome = wasHome,
        totalPoints = totalPoints,
        minutes = minutes,
        goalsScored = goalsScored,
        assists = assists,
        cleanSheets = cleanSheets,
        bonus = bonus,
        bps = bps,
        expectedGoals = expectedGoals.toDoubleOrZero(),
        expectedAssists = expectedAssists.toDoubleOrZero(),
        value = value,
    )

private fun SummaryPastDto.toDomain(): SeasonHistory = SeasonHistory(
    seasonName = seasonName,
    totalPoints = totalPoints,
    minutes = minutes,
    goalsScored = goalsScored,
    assists = assists,
    cleanSheets = cleanSheets,
    startCost = startCost,
    endCost = endCost,
)
