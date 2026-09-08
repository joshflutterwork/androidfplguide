package com.fplguide.domain.model

/** A league fixture, already joined with both teams and enriched with FDR ratings. */
data class Fixture(
    val id: Int,
    val event: Int,
    val home: Team,
    val away: Team,
    val homeDifficulty: Int,
    val awayDifficulty: Int,
    val homeScore: Int?,
    val awayScore: Int?,
    val kickoffTime: String?,
    val started: Boolean,
    val finished: Boolean,
    val minutes: Int,
) {
    /** `true` once either side has a kickoff past and the fixture hasn't finished. */
    val isLive: Boolean get() = started && !finished

    /** "2-1" when available, "vs" otherwise. */
    val scoreLabel: String
        get() = if (homeScore != null && awayScore != null) "$homeScore-$awayScore" else "vs"
}
