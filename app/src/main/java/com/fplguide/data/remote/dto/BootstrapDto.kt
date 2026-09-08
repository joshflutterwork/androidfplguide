package com.fplguide.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * `GET /api/bootstrap-static/` — ~1.7 MB covering every player, team and gameweek.
 *
 * Only the fields the app actually renders are declared; `ignoreUnknownKeys` in the Json
 * config absorbs the other ~70 element fields so an FPL schema addition can't crash us.
 */
@Serializable
data class BootstrapDto(
    val elements: List<ElementDto> = emptyList(),
    val teams: List<TeamDto> = emptyList(),
    val events: List<EventDto> = emptyList(),
    @SerialName("element_types") val elementTypes: List<ElementTypeDto> = emptyList(),
)

@Serializable
data class ElementDto(
    val id: Int,
    val code: Int,
    @SerialName("first_name") val firstName: String = "",
    @SerialName("second_name") val secondName: String = "",
    @SerialName("web_name") val webName: String = "",
    val team: Int = 0,
    @SerialName("element_type") val elementType: Int = 0,
    val status: String = "a",
    val news: String = "",
    @SerialName("chance_of_playing_next_round") val chanceOfPlayingNextRound: Int? = null,
    @SerialName("now_cost") val nowCost: Int = 0,
    @SerialName("cost_change_event") val costChangeEvent: Int = 0,
    @SerialName("total_points") val totalPoints: Int = 0,
    @SerialName("event_points") val eventPoints: Int = 0,
    @SerialName("points_per_game") val pointsPerGame: String = "0",
    val form: String = "0",
    @SerialName("selected_by_percent") val selectedByPercent: String = "0",
    val minutes: Int = 0,
    val starts: Int = 0,
    @SerialName("goals_scored") val goalsScored: Int = 0,
    val assists: Int = 0,
    @SerialName("clean_sheets") val cleanSheets: Int = 0,
    @SerialName("goals_conceded") val goalsConceded: Int = 0,
    val saves: Int = 0,
    val bonus: Int = 0,
    val bps: Int = 0,
    @SerialName("yellow_cards") val yellowCards: Int = 0,
    @SerialName("red_cards") val redCards: Int = 0,
    @SerialName("penalties_order") val penaltiesOrder: Int? = null,
    val influence: String = "0",
    val creativity: String = "0",
    val threat: String = "0",
    @SerialName("ict_index") val ictIndex: String = "0",
    @SerialName("expected_goals") val expectedGoals: String = "0",
    @SerialName("expected_assists") val expectedAssists: String = "0",
    @SerialName("expected_goal_involvements") val expectedGoalInvolvements: String = "0",
    @SerialName("expected_goals_conceded") val expectedGoalsConceded: String = "0",
    @SerialName("expected_goals_per_90") val expectedGoalsPer90: Double = 0.0,
    @SerialName("expected_assists_per_90") val expectedAssistsPer90: Double = 0.0,
    @SerialName("defensive_contribution") val defensiveContribution: Int = 0,
    @SerialName("transfers_in_event") val transfersInEvent: Int = 0,
    @SerialName("transfers_out_event") val transfersOutEvent: Int = 0,
    @SerialName("value_season") val valueSeason: String = "0",
)

@Serializable
data class TeamDto(
    val id: Int,
    val code: Int = 0,
    val name: String = "",
    @SerialName("short_name") val shortName: String = "",
)

@Serializable
data class ElementTypeDto(
    val id: Int,
    @SerialName("singular_name") val singularName: String = "",
    @SerialName("singular_name_short") val singularNameShort: String = "",
)

@Serializable
data class EventDto(
    val id: Int,
    val name: String = "",
    @SerialName("deadline_time") val deadlineTime: String = "",
    val finished: Boolean = false,
    @SerialName("is_current") val isCurrent: Boolean = false,
    @SerialName("is_next") val isNext: Boolean = false,
    @SerialName("average_entry_score") val averageEntryScore: Int = 0,
    @SerialName("highest_score") val highestScore: Int? = null,
)
