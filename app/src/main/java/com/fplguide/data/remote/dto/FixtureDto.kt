package com.fplguide.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One row of `GET /api/fixtures/` — the whole season in one payload. Scores are null
 * until the fixture starts; `event` is the gameweek number.
 */
@Serializable
data class FixtureDto(
    val id: Int = 0,
    val event: Int = 0,
    @SerialName("team_h") val teamHome: Int = 0,
    @SerialName("team_a") val teamAway: Int = 0,
    @SerialName("team_h_difficulty") val teamHomeDifficulty: Int = 3,
    @SerialName("team_a_difficulty") val teamAwayDifficulty: Int = 3,
    @SerialName("team_h_score") val teamHomeScore: Int? = null,
    @SerialName("team_a_score") val teamAwayScore: Int? = null,
    @SerialName("kickoff_time") val kickoffTime: String? = null,
    val started: Boolean = false,
    val finished: Boolean = false,
    val minutes: Int = 0,
)
