package com.fplguide.presentation.playerdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.core.util.formatDecimal
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.Position

/** A label/value pair rendered as a small stat card. */
private data class Stat(val label: String, val value: String)

/**
 * Season overview in a two-column card grid. Stats are grouped so every position gets
 * a meaningful block — goalkeeping/saves only for keepers, xG for attackers, etc.
 */
@Composable
fun StatGrid(player: Player, modifier: Modifier = Modifier) {
    val stats = buildStats(player)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        stats.chunked(2).forEach { rowStats ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowStats.forEach { stat ->
                    StatCard(stat, Modifier.weight(1f))
                }
                // Odd count: pad the empty cell so the last card keeps half width.
                if (rowStats.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun StatCard(stat: Stat, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = stat.label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stat.value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (stat.label == "Total points") {
                    FplGreen
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            )
        }
    }
}

private fun buildStats(player: Player): List<Stat> = buildList {
    add(Stat("Total points", player.totalPoints.toString()))
    add(Stat("Points / game", player.pointsPerGame.formatDecimal()))
    add(Stat("Form", player.form.formatDecimal()))
    add(Stat("Ownership", "${player.selectedByPercent.formatDecimal(1)}%"))
    add(Stat("Minutes", player.minutes.toString()))
    add(Stat("Starts", player.starts.toString()))
    add(Stat("Goals", player.goalsScored.toString()))
    add(Stat("Assists", player.assists.toString()))
    if (player.position == Position.GOALKEEPER) {
        add(Stat("Saves", player.saves.toString()))
        add(Stat("Clean sheets", player.cleanSheets.toString()))
    } else {
        add(Stat("Clean sheets", player.cleanSheets.toString()))
        add(Stat("Bonus", player.bonus.toString()))
    }
    add(Stat("xG", player.expectedGoals.formatDecimal(2)))
    add(Stat("xA", player.expectedAssists.formatDecimal(2)))
    add(Stat("xGI", player.expectedGoalInvolvements.formatDecimal(2)))
    add(Stat("ICT index", player.ictIndex.formatDecimal()))
    add(Stat("Value (pts/£m)", player.valuePerMillion.formatDecimal(2)))
    add(Stat("Yellow / red", "${player.yellowCards} / ${player.redCards}"))
}
