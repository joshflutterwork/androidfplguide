package com.fplguide.presentation.players.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.fplguide.domain.model.PlayerSort

@Composable
fun SortMenu(
    current: PlayerSort,
    descending: Boolean,
    onSortChange: (PlayerSort) -> Unit,
    onToggleDirection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Sort,
            contentDescription = "Sort players",
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = {
                Text(if (descending) "Descending" else "Ascending")
            },
            leadingIcon = {
                Icon(
                    imageVector = if (descending) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
                    contentDescription = null,
                )
            },
            onClick = {
                onToggleDirection()
                expanded = false
            },
        )
        PlayerSort.entries.forEach { sort ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = sort.label + if (sort == current) " ✓" else "",
                    )
                },
                onClick = {
                    onSortChange(sort)
                    expanded = false
                },
            )
        }
    }
}
