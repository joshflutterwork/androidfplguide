package com.fplguide.presentation.players.component

import androidx.compose.ui.unit.dp

/**
 * Shared column geometry for the player list.
 *
 * The header strip and every [PlayerRow] read these same widths, which is what makes the
 * numeric columns line up down the whole list instead of drifting with content width.
 */
object PlayerListMetrics {
    val PhotoSize = 44.dp
    val StatColumnWidth = 62.dp
    val PointsColumnWidth = 46.dp
    val RowHorizontalPadding = 12.dp
    /** Photo + gap: the header's "PLAYER" caption starts where the name does. */
    val NameColumnInset = PhotoSize + 12.dp
}
