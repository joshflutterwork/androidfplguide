package com.fplguide.presentation.playerdetail.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.domain.model.GameweekHistory

/**
 * Per-gameweek points as a bar chart drawn straight on a [Canvas] — no chart library
 * needed for a single series. Green for scoring weeks, red for blanks/negatives,
 * baseline drawn at zero.
 */
@Composable
fun PointsChart(
    history: List<GameweekHistory>,
    modifier: Modifier = Modifier,
) {
    if (history.isEmpty()) return

    val positive = FplGreen
    val negative = Color(0xFFEB5757)
    val baseline = MaterialTheme.colorScheme.outline

    Column(modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
        ) {
            val maxPoints = history.maxOf { it.totalPoints }.coerceAtLeast(1)
            val minPoints = history.minOf { it.totalPoints }.coerceAtMost(0)
            val range = (maxPoints - minPoints).toFloat().coerceAtLeast(1f)
            val zeroY = size.height * (maxPoints.toFloat() / range)
            val slotWidth = size.width / history.size
            val barWidth = slotWidth * 0.6f

            drawLine(
                color = baseline,
                start = Offset(0f, zeroY),
                end = Offset(size.width, zeroY),
                strokeWidth = 2f,
            )

            history.forEachIndexed { index, gameweek ->
                val points = gameweek.totalPoints
                val barHeight = (kotlin.math.abs(points) / range) * size.height
                val left = index * slotWidth + (slotWidth - barWidth) / 2
                val color = if (points >= 0) positive else negative
                val top = if (points >= 0) zeroY - barHeight else zeroY
                drawRoundRect(
                    color = color,
                    topLeft = Offset(left, top.toFloat()),
                    size = Size(barWidth, barHeight.toFloat()),
                    cornerRadius = CornerRadius(6f, 6f),
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "GW ${history.first().round}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "GW ${history.last().round}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
