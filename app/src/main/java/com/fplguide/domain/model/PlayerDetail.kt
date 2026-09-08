package com.fplguide.domain.model

/** [Player] plus everything from `element-summary/{id}`. */
data class PlayerDetail(
    val player: Player,
    val upcomingFixtures: List<UpcomingFixture>,
    val history: List<GameweekHistory>,
    val pastSeasons: List<SeasonHistory>,
)

data class UpcomingFixture(
    val eventId: Int?,
    val eventName: String?,
    val opponent: Team?,
    val isHome: Boolean,
    val difficulty: Int,
    val kickoffTime: String?,
)

data class GameweekHistory(
    val round: Int,
    val opponent: Team?,
    val wasHome: Boolean,
    val totalPoints: Int,
    val minutes: Int,
    val goalsScored: Int,
    val assists: Int,
    val cleanSheets: Int,
    val bonus: Int,
    val bps: Int,
    val expectedGoals: Double,
    val expectedAssists: Double,
    val value: Int,
) {
    val priceLabel: String get() = "£%.1fm".format(value / 10.0)
}

data class SeasonHistory(
    val seasonName: String,
    val totalPoints: Int,
    val minutes: Int,
    val goalsScored: Int,
    val assists: Int,
    val cleanSheets: Int,
    val startCost: Int,
    val endCost: Int,
)
