package com.fplguide.presentation.players.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.core.designsystem.FplPriceDown
import com.fplguide.core.designsystem.FplPurpleSurface
import com.fplguide.core.designsystem.FplPurpleSurfaceDark
import com.fplguide.core.designsystem.FplRowBorder
import com.fplguide.core.designsystem.FplTextStyles
import com.fplguide.core.designsystem.color
import com.fplguide.core.util.formatDecimal
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerSort
import com.fplguide.domain.model.Position

/**
 * Anatomy per requirements.md §6.1: photo left (status dot on its corner), identity in the
 * middle, then two fixed-width numeric columns — the active sort stat and total points.
 *
 * Both numeric columns take their widths from [PlayerListMetrics] and render with tabular
 * figures, so digits line up vertically across the list; that alignment, plus the hairline
 * border separating the card from the near-identical purple background, is what makes a
 * dense stats list scannable rather than a wall of text.
 */
@Composable
fun PlayerRow(
    player: Player,
    sort: PlayerSort,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(FplPurpleSurface)
            .border(1.dp, FplRowBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(
                horizontal = PlayerListMetrics.RowHorizontalPadding,
                vertical = 10.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PlayerPhoto(player)

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = player.webName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                PositionBadge(player.position)
                Text(
                    text = player.team.shortName.uppercase(),
                    style = FplTextStyles.Badge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "•",
                    style = FplTextStyles.Badge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = player.priceLabel,
                    style = FplTextStyles.Money,
                    color = when {
                        player.costChangeEvent > 0 -> FplGreen
                        player.costChangeEvent < 0 -> FplPriceDown
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }

        Spacer(Modifier.width(8.dp))

        // The only green number in the row — it marks what the list is currently sorted by.
        Text(
            text = statValue(player, sort),
            style = FplTextStyles.StatValue,
            color = FplGreen,
            maxLines = 1,
            modifier = Modifier.width(PlayerListMetrics.StatColumnWidth),
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = player.totalPoints.toString(),
            style = FplTextStyles.StatValue.copy(textAlign = TextAlign.Center),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            modifier = Modifier
                .width(PlayerListMetrics.PointsColumnWidth)
                .clip(RoundedCornerShape(8.dp))
                .background(FplPurpleSurfaceDark)
                .padding(vertical = 6.dp),
        )
    }
}

/** Column captions above the list, aligned to the same [PlayerListMetrics] widths. */
@Composable
fun PlayerListHeader(
    sort: PlayerSort,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = PlayerListMetrics.RowHorizontalPadding + 1.dp,
                vertical = 6.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(PlayerListMetrics.NameColumnInset))
        Text(
            text = "PLAYER",
            style = FplTextStyles.SectionHeader,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = sort.columnLabel.uppercase(),
            style = FplTextStyles.SectionHeader.copy(textAlign = TextAlign.End),
            color = FplGreen,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(PlayerListMetrics.StatColumnWidth),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "PTS",
            style = FplTextStyles.SectionHeader.copy(textAlign = TextAlign.Center),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(PlayerListMetrics.PointsColumnWidth),
        )
    }
}

@Composable
private fun PositionBadge(position: Position) {
    val tint = position.color()
    Text(
        text = position.shortLabel,
        style = FplTextStyles.Badge,
        color = tint,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(tint.copy(alpha = 0.16f))
            .padding(horizontal = 5.dp, vertical = 2.dp),
    )
}

@Composable
private fun PlayerPhoto(player: Player) {
    Box {
        AsyncImage(
            model = player.photoUrl,
            contentDescription = player.webName,
            modifier = Modifier
                .size(PlayerListMetrics.PhotoSize)
                .clip(RoundedCornerShape(12.dp))
                .background(FplPurpleSurfaceDark),
        )
        if (player.status.isRisky) {
            // Ringed so the dot stays legible against whatever the photo happens to show.
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(FplPurpleSurface)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(player.status.color()),
            )
        }
    }
}

/** Sort-dependent column: integers plain, decimals to one place, transfers abbreviated. */
private fun statValue(player: Player, sort: PlayerSort): String {
    val value = sort.selector(player)
    return when (sort) {
        PlayerSort.TOTAL_POINTS -> value.toInt().toString()
        PlayerSort.NET_TRANSFERS -> formatCompact(value.toInt())
        PlayerSort.PRICE_HIGH -> player.priceLabel
        PlayerSort.SELECTED_BY -> "${value.formatDecimal()}%"
        else -> value.formatDecimal()
    }
}

/** 118398 -> "118k" so the transfers column never overflows its fixed width. */
private fun formatCompact(value: Int): String {
    val abs = kotlin.math.abs(value)
    val sign = if (value < 0) "-" else ""
    return when {
        abs >= 1_000_000 -> "$sign${abs / 100_000 / 10.0}m"
        abs >= 1_000 -> "$sign${abs / 1_000}k"
        else -> value.toString()
    }
}
