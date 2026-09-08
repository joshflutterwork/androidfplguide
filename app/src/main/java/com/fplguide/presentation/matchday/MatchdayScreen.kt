package com.fplguide.presentation.matchday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.core.designsystem.FplPurpleSurfaceDark
import com.fplguide.core.ui.component.EmptyState
import com.fplguide.core.ui.component.ErrorState
import com.fplguide.core.ui.component.LoadingState
import com.fplguide.core.ui.component.toMessage
import com.fplguide.presentation.matchday.component.FixtureRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchdayScreen(
    viewModel: MatchdayViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Text(
            text = "Matchday",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )

        GameweekSelector(
            selectedGameweek = state.selectedGameweek,
            isCurrentWeek = state.isViewingCurrentWeek,
            canGoPrevious = state.canGoPrevious,
            canGoNext = state.canGoNext,
            onPrevious = { viewModel.changeGameweek(-1) },
            onNext = { viewModel.changeGameweek(+1) },
            onJumpToCurrent = viewModel::jumpToCurrentGameweek,
        )

        when {
            state.isLoading -> LoadingState(message = "Loading fixtures…")
            state.error != null && !state.hasData -> ErrorState(
                message = state.error!!.toMessage(),
                onRetry = { viewModel.load() },
            )
            state.fixturesForSelectedWeek.isEmpty() -> EmptyState("No fixtures found for this gameweek")
            else -> PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.load(forceRefresh = true) },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 4.dp),
                ) {
                    items(state.fixturesForSelectedWeek, key = { it.id }) { fixture ->
                        FixtureRow(
                            fixture = fixture,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                    }
                }
            }
        }
    }
}

/** ‹ Gameweek N › stepper. Flipping weeks is a pure state change — nothing is re-fetched. */
@Composable
private fun GameweekSelector(
    selectedGameweek: Int,
    isCurrentWeek: Boolean,
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onJumpToCurrent: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(FplPurpleSurfaceDark),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPrevious, enabled = canGoPrevious) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous gameweek",
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Gameweek $selectedGameweek",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            if (isCurrentWeek) {
                Text(
                    text = "CURRENT",
                    style = MaterialTheme.typography.labelSmall,
                    color = FplGreen,
                )
            } else {
                TextButton(onClick = onJumpToCurrent) {
                    Text("Jump to current")
                }
            }
        }
        IconButton(onClick = onNext, enabled = canGoNext) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next gameweek",
            )
        }
    }
}
