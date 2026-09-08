package com.fplguide.domain.model

enum class Position(val elementType: Int, val shortLabel: String, val label: String) {
    GOALKEEPER(1, "GKP", "Goalkeeper"),
    DEFENDER(2, "DEF", "Defender"),
    MIDFIELDER(3, "MID", "Midfielder"),
    FORWARD(4, "FWD", "Forward");

    companion object {
        fun fromElementType(elementType: Int): Position =
            entries.firstOrNull { it.elementType == elementType } ?: MIDFIELDER
    }
}
