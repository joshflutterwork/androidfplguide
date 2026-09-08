package com.fplguide.presentation.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.core.designsystem.FplOnGreen
import com.fplguide.core.designsystem.FplPurpleSurfaceDark
import com.fplguide.core.designsystem.FplTextDim
import com.fplguide.core.designsystem.FplTextStyles
import com.fplguide.core.designsystem.FplPurple
import com.fplguide.core.designsystem.FplRowBorder
import com.fplguide.core.ui.component.EmptyState
import com.fplguide.core.ui.component.ErrorState
import com.fplguide.core.ui.component.LoadingState
import com.fplguide.core.ui.component.toMessage
import com.fplguide.domain.model.PlayerQuery
import com.fplguide.domain.model.PlayerSort
import com.fplguide.domain.model.Position
import com.fplguide.presentation.players.component.FilterSheet
import com.fplguide.presentation.players.component.PlayerListHeader
import com.fplguide.presentation.players.component.PlayerRow
import com.fplguide.presentation.players.component.SortMenu

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayersScreen(
    onPlayerClick: (Int) -> Unit,
    viewModel: PlayersViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showFilters by rememberSaveable { mutableStateOf(false) }
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Header(
            gameweekLabel = state.currentGameweek?.let { "${it.id}" },
            resultCount = state.filtered.size,
            sort = state.query.sort,
            descending = state.query.descending,
            onSortChange = viewModel::onSortChange,
            onToggleDirection = viewModel::onToggleSortDirection,
        )

        SearchField(
            value = state.query.search,
            onValueChange = viewModel::onSearchChange,
        )

        FilterRow(
            query = state.query,
            onQueryChange = viewModel::onQueryChange,
            onOpenFilters = { showFilters = true },
        )

        when {
            state.isLoading -> LoadingState()
            state.error != null && !state.hasData -> ErrorState(
                message = state.error!!.toMessage(),
                onRetry = { viewModel.load() },
            )
            state.filtered.isEmpty() -> EmptyState("No players match your filters")
            else -> PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.load(forceRefresh = true) },
            ) {
                if (state.error != null && state.hasData) {
                    StaleBanner(message = state.error!!.toMessage())
                }
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 12.dp),
                ) {
                    stickyHeader {
                        PlayerListHeader(
                            sort = state.query.sort,
                            modifier = Modifier
                                .background(FplPurple)
                                .padding(horizontal = 16.dp),
                        )
                    }
                    items(state.filtered, key = { it.id }) { player ->
                        PlayerRow(
                            player = player,
                            sort = state.query.sort,
                            onClick = { onPlayerClick(player.id) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp),
                        )
                    }
                }
            }
        }
    }

    if (showFilters) {
        FilterSheet(
            query = state.query,
            teams = state.teams,
            onQueryChange = viewModel::onQueryChange,
            onReset = viewModel::resetFilters,
            onDismiss = { showFilters = false },
        )
    }
}

@Composable
private fun Header(
    gameweekLabel: String?,
    resultCount: Int,
    sort: PlayerSort,
    descending: Boolean,
    onSortChange: (PlayerSort) -> Unit,
    onToggleDirection: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(FplGreen),
            contentAlignment = Alignment.Center,
        ) {
            Text("F", style = MaterialTheme.typography.titleLarge, color = FplOnGreen)
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = "FPL Guide",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (resultCount > 0) {
                Text(
                    text = "$resultCount players",
                    style = FplTextStyles.StatLabel,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        gameweekLabel?.let { gw ->
            Text(
                text = "GW $gw",
                style = FplTextStyles.Badge,
                color = FplGreen,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(FplPurpleSurfaceDark)
                    .border(1.dp, FplRowBorder, RoundedCornerShape(8.dp))
                    .padding(horizontal = 9.dp, vertical = 5.dp),
            )
        }
        Spacer(Modifier.width(4.dp))
        SortMenu(
            current = sort,
            descending = descending,
            onSortChange = onSortChange,
            onToggleDirection = onToggleDirection,
        )
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        placeholder = {
            Text(
                "Search player or team",
                style = MaterialTheme.typography.bodyMedium,
                color = FplTextDim,
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = null, tint = FplTextDim)
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = FplPurpleSurfaceDark,
            unfocusedContainerColor = FplPurpleSurfaceDark,
            focusedBorderColor = FplGreen,
            unfocusedBorderColor = Color.Transparent,
        ),
    )
}

@Composable
private fun FilterRow(
    query: PlayerQuery,
    onQueryChange: (PlayerQuery) -> Unit,
    onOpenFilters: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
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
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = FplPurpleSurfaceDark,
                    labelColor = FplTextDim,
                    selectedContainerColor = FplGreen,
                    selectedLabelColor = FplOnGreen,
                ),
            )
        }
        Spacer(Modifier.weight(1f))
        BadgedBox(
            badge = {
                if (query.activeFilterCount > 0) {
                    Badge { Text(query.activeFilterCount.toString()) }
                }
            },
        ) {
            IconButton(onClick = onOpenFilters) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Open filters",
                )
            }
        }
    }
}

@Composable
private fun StaleBanner(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
    )
}
