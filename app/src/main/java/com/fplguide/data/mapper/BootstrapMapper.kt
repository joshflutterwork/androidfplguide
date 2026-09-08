package com.fplguide.data.mapper

import com.fplguide.core.util.toDoubleOrZero
import com.fplguide.data.remote.dto.BootstrapDto
import com.fplguide.data.remote.dto.ElementDto
import com.fplguide.data.remote.dto.EventDto
import com.fplguide.data.remote.dto.TeamDto
import com.fplguide.domain.model.Gameweek
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerStatus
import com.fplguide.domain.model.Position
import com.fplguide.domain.model.Team

/** One mapped `bootstrap-static` payload: the only shape the cache and UI ever see. */
data class BootstrapSnapshot(
    val players: List<Player>,
    val teams: List<Team>,
    val gameweeks: List<Gameweek>,
)

/**
 * Maps the whole bootstrap payload in one pass. Teams are indexed first because every
 * element references its team by id; a missing team degrades to a placeholder instead
 * of dropping the player.
 */
fun BootstrapDto.toDomain(): BootstrapSnapshot {
    val teams = teams.map { it.toDomain() }
    val teamsById = teams.associateBy { it.id }

    return BootstrapSnapshot(
        players = elements.map { it.toDomain(teamsById) },
        teams = teams,
        gameweeks = events.map { it.toDomain() },
    )
}

fun TeamDto.toDomain(): Team = Team(
    id = id,
    code = code,
    name = name,
    shortName = shortName,
)

fun EventDto.toDomain(): Gameweek = Gameweek(
    id = id,
    name = name,
    deadlineTime = deadlineTime,
    finished = finished,
    isCurrent = isCurrent,
    isNext = isNext,
    averageEntryScore = averageEntryScore,
    highestScore = highestScore,
)

fun ElementDto.toDomain(teamsById: Map<Int, Team>): Player = Player(
    id = id,
    code = code,
    webName = webName,
    fullName = listOf(firstName.trim(), secondName.trim())
        .filter { it.isNotEmpty() }
        .joinToString(" "),
    team = teamsById[team] ?: Team(id = team, code = 0, name = "Unknown", shortName = "UNK"),
    position = Position.fromElementType(elementType),
    status = PlayerStatus.fromCode(status),
    news = news,
    chanceOfPlayingNextRound = chanceOfPlayingNextRound,
    nowCost = nowCost,
    costChangeEvent = costChangeEvent,
    totalPoints = totalPoints,
    eventPoints = eventPoints,
    pointsPerGame = pointsPerGame.toDoubleOrZero(),
    form = form.toDoubleOrZero(),
    selectedByPercent = selectedByPercent.toDoubleOrZero(),
    minutes = minutes,
    starts = starts,
    goalsScored = goalsScored,
    assists = assists,
    cleanSheets = cleanSheets,
    goalsConceded = goalsConceded,
    saves = saves,
    bonus = bonus,
    bps = bps,
    yellowCards = yellowCards,
    redCards = redCards,
    penaltiesOrder = penaltiesOrder,
    influence = influence.toDoubleOrZero(),
    creativity = creativity.toDoubleOrZero(),
    threat = threat.toDoubleOrZero(),
    ictIndex = ictIndex.toDoubleOrZero(),
    expectedGoals = expectedGoals.toDoubleOrZero(),
    expectedAssists = expectedAssists.toDoubleOrZero(),
    expectedGoalInvolvements = expectedGoalInvolvements.toDoubleOrZero(),
    expectedGoalsConceded = expectedGoalsConceded.toDoubleOrZero(),
    expectedGoalsPer90 = expectedGoalsPer90,
    expectedAssistsPer90 = expectedAssistsPer90,
    defensiveContribution = defensiveContribution,
    transfersInEvent = transfersInEvent,
    transfersOutEvent = transfersOutEvent,
    valueSeason = valueSeason.toDoubleOrZero(),
)
