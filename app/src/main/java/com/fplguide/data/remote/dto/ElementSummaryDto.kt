package com.fplguide.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** `GET /api/element-summary/{playerId}/` */
@Serializable
data class ElementSummaryDto(
    val fixtures: List<SummaryFixtureDto> = emptyList(),
    val history: List<SummaryHistoryDto> = emptyList(),
    @SerialName("history_past") val historyPast: List<SummaryPastDto> = emptyList(),
)

@Serializable
data class SummaryFixtureDto(
    val id: Int = 0,
    @SerialName("team_h") val teamHome: Int = 0,
    @SerialName("team_a") val teamAway: Int = 0,
    val event: Int? = null,
    @SerialName("event_name") val eventName: String? = null,
    @SerialName("is_home") val isHome: Boolean = false,
    val difficulty: Int = 3,
    @SerialName("kickoff_time") val kickoffTime: String? = null,
    val finished: Boolean = false,
)

@Serializable
data class SummaryHistoryDto(
    val round: Int = 0,
    @SerialName("opponent_team") val opponentTeam: Int = 0,
    @SerialName("was_home") val wasHome: Boolean = false,
    @SerialName("total_points") val totalPoints: Int = 0,
    val minutes: Int = 0,
    @SerialName("goals_scored") val goalsScored: Int = 0,
    val assists: Int = 0,
    @SerialName("clean_sheets") val cleanSheets: Int = 0,
    val bonus: Int = 0,
    val bps: Int = 0,
    @SerialName("expected_goals") val expectedGoals: String = "0",
    @SerialName("expected_assists") val expectedAssists: String = "0",
    val value: Int = 0,
)

@Serializable
data class SummaryPastDto(
    @SerialName("season_name") val seasonName: String = "",
    @SerialName("total_points") val totalPoints: Int = 0,
    val minutes: Int = 0,
    @SerialName("goals_scored") val goalsScored: Int = 0,
    val assists: Int = 0,
    @SerialName("clean_sheets") val cleanSheets: Int = 0,
    @SerialName("start_cost") val startCost: Int = 0,
    @SerialName("end_cost") val endCost: Int = 0,
)
