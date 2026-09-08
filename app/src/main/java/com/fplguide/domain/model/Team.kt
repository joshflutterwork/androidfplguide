package com.fplguide.domain.model

data class Team(
    val id: Int,
    val code: Int,
    val name: String,
    val shortName: String,
) {
    /** Premier League CDN badge, keyed by team [code] (not [id]). */
    val badgeUrl: String
        get() = "https://resources.premierleague.com/premierleague/badges/70/t$code.png"
}
