package com.fplguide.domain.model

/** Maps the single-letter `status` field on an FPL element. */
enum class PlayerStatus(val code: String, val label: String) {
    AVAILABLE("a", "Available"),
    INJURED("i", "Injured"),
    DOUBTFUL("d", "Doubtful"),
    SUSPENDED("s", "Suspended"),
    UNAVAILABLE("u", "Unavailable"),
    NOT_IN_SQUAD("n", "Not in squad");

    val isRisky: Boolean get() = this != AVAILABLE

    companion object {
        fun fromCode(code: String): PlayerStatus =
            entries.firstOrNull { it.code == code } ?: AVAILABLE
    }
}
