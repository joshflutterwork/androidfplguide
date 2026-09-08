package com.fplguide.domain.model

/**
 * A single FPL asset, flattened from `bootstrap-static.elements` and joined with its [team].
 *
 * All money is stored in tenths of a million exactly as the API returns it ([nowCost] = 125 -> £12.5m);
 * formatting is a presentation concern, so it stays out of this model apart from [priceLabel].
 */
data class Player(
    val id: Int,
    val code: Int,
    val webName: String,
    val fullName: String,
    val team: Team,
    val position: Position,
    val status: PlayerStatus,
    val news: String,
    val chanceOfPlayingNextRound: Int?,
    val nowCost: Int,
    val costChangeEvent: Int,
    val totalPoints: Int,
    val eventPoints: Int,
    val pointsPerGame: Double,
    val form: Double,
    val selectedByPercent: Double,
    val minutes: Int,
    val starts: Int,
    val goalsScored: Int,
    val assists: Int,
    val cleanSheets: Int,
    val goalsConceded: Int,
    val saves: Int,
    val bonus: Int,
    val bps: Int,
    val yellowCards: Int,
    val redCards: Int,
    val penaltiesOrder: Int?,
    val influence: Double,
    val creativity: Double,
    val threat: Double,
    val ictIndex: Double,
    val expectedGoals: Double,
    val expectedAssists: Double,
    val expectedGoalInvolvements: Double,
    val expectedGoalsConceded: Double,
    val expectedGoalsPer90: Double,
    val expectedAssistsPer90: Double,
    val defensiveContribution: Int,
    val transfersInEvent: Int,
    val transfersOutEvent: Int,
    val valueSeason: Double,
) {
    /** e.g. "£12.5m" */
    val priceLabel: String get() = "£%.1fm".format(nowCost / 10.0)

    /** Net transfers this gameweek — the headline "who is being bought" number. */
    val netTransfersEvent: Int get() = transfersInEvent - transfersOutEvent

    /** Points per million spent. Guards against the divide-by-zero on a £0.0m placeholder. */
    val valuePerMillion: Double
        get() = if (nowCost == 0) 0.0 else totalPoints / (nowCost / 10.0)

    val goalInvolvements: Int get() = goalsScored + assists

    val photoUrl: String
        get() = "https://resources.premierleague.com/premierleague/photos/players/250x250/p$code.png"
}
