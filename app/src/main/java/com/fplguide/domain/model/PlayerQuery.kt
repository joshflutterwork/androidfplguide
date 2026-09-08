package com.fplguide.domain.model

/** How the player list is sorted. `selector` keeps the comparator logic next to the label. */
enum class PlayerSort(
    val label: String,
    /** Short caption for the list's numeric column header — must fit ~62dp. */
    val columnLabel: String,
    val selector: (Player) -> Double,
) {
    TOTAL_POINTS("Total points", "Pts", { it.totalPoints.toDouble() }),
    FORM("Form", "Form", { it.form }),
    POINTS_PER_GAME("Points / game", "PPG", { it.pointsPerGame }),
    PRICE_HIGH("Price", "Price", { it.nowCost.toDouble() }),
    VALUE("Value (pts/£m)", "Value", { it.valuePerMillion }),
    SELECTED_BY("Ownership %", "Own", { it.selectedByPercent }),
    ICT("ICT index", "ICT", { it.ictIndex }),
    XGI("Expected G+A", "xGI", { it.expectedGoalInvolvements }),
    NET_TRANSFERS("Transfers in (GW)", "In", { it.netTransfersEvent.toDouble() }),
}

/**
 * The full filter/sort state for the player list. Kept in the domain layer so the
 * filtering rules are unit-testable without Compose or Android.
 */
data class PlayerQuery(
    val search: String = "",
    val positions: Set<Position> = emptySet(),
    val teamIds: Set<Int> = emptySet(),
    val maxCost: Int? = null,
    val minMinutes: Int = 0,
    val availableOnly: Boolean = false,
    val sort: PlayerSort = PlayerSort.TOTAL_POINTS,
    val descending: Boolean = true,
) {
    val activeFilterCount: Int
        get() = positions.size.coerceAtMost(1) +
            teamIds.size.coerceAtMost(1) +
            (if (maxCost != null) 1 else 0) +
            (if (minMinutes > 0) 1 else 0) +
            (if (availableOnly) 1 else 0)

    val hasActiveFilters: Boolean get() = activeFilterCount > 0
}
