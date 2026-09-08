package com.fplguide.presentation.players.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fplguide.domain.model.PlayerQuery
import com.fplguide.domain.model.Position
import com.fplguide.domain.model.Team

/** Slider bounds in API money units (tenths of millions): £4.0m … £15.0m. */
private const val MIN_PRICE = 40f
private const val MAX_PRICE = 150f

private const val MIN_MINUTES = 0
private const val MAX_MINUTES = 90

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterSheet(
    query: PlayerQuery,
    teams: List<Team>,
    onQueryChange: (PlayerQuery) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
        ) {
            Text("Filters", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            SectionLabel("Position")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Position.entries.forEach { position ->
                    FilterChip(
                        selected = position in query.positions,
                        onClick = {
                            val next = if (position in query.positions) {
                                query.positions - position
                            } else {
                                query.positions + position
                            }
                            onQueryChange(query.copy(positions = next))
                        },
                        label = { Text(position.shortLabel) },
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Team")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                teams.sortedBy { it.shortName }.forEach { team ->
                    FilterChip(
                        selected = team.id in query.teamIds,
                        onClick = {
                            val next = if (team.id in query.teamIds) {
                                query.teamIds - team.id
                            } else {
                                query.teamIds + team.id
                            }
                            onQueryChange(query.copy(teamIds = next))
                        },
                        label = { Text(team.shortName.uppercase()) },
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel(
                if (query.maxCost == null) "Max price: any" else "Max price: £%.1fm".format(query.maxCost!! / 10.0),
            )
            Slider(
                value = query.maxCost?.toFloat() ?: MAX_PRICE,
                onValueChange = { raw ->
                    val snapped = (raw / 5f).toInt() * 5
                    val next = if (snapped >= MAX_PRICE.toInt()) null else snapped
                    onQueryChange(query.copy(maxCost = next))
                },
                valueRange = MIN_PRICE..MAX_PRICE,
                steps = ((MAX_PRICE - MIN_PRICE) / 5f).toInt() - 1,
            )

            Spacer(Modifier.height(20.dp))
            SectionLabel("Min minutes: ${query.minMinutes}")
            Slider(
                value = query.minMinutes.toFloat(),
                onValueChange = { raw ->
                    onQueryChange(query.copy(minMinutes = raw.toInt().coerceIn(MIN_MINUTES, MAX_MINUTES)))
                },
                valueRange = MIN_MINUTES.toFloat()..MAX_MINUTES.toFloat(),
            )

            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("Available only", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Hide injured, doubtful and suspended players",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Switch(
                    checked = query.availableOnly,
                    onCheckedChange = { checked ->
                        onQueryChange(query.copy(availableOnly = checked))
                    },
                )
            }

            Spacer(Modifier.height(24.dp))
            Button(onClick = onReset, modifier = Modifier.fillMaxWidth()) {
                Text("Reset filters")
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(bottom = 8.dp),
    )
}
