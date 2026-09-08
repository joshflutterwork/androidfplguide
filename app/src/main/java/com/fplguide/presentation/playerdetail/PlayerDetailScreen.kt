package com.fplguide.presentation.playerdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fplguide.core.designsystem.color
import com.fplguide.core.ui.component.ErrorState
import com.fplguide.core.ui.component.LoadingState
import com.fplguide.core.ui.component.toMessage
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerDetail
import com.fplguide.presentation.playerdetail.component.FixtureList
import com.fplguide.presentation.playerdetail.component.PointsChart
import com.fplguide.presentation.playerdetail.component.SeasonHistoryTable
import com.fplguide.presentation.playerdetail.component.StatGrid

@Composable
fun PlayerDetailScreen(
    onBack: () -> Unit,
    viewModel: PlayerDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (val current = state) {
        is PlayerDetailUiState.Loading -> LoadingState()
        is PlayerDetailUiState.Error -> ErrorState(
            message = current.error.toMessage(),
            onRetry = viewModel::retry,
        )
        is PlayerDetailUiState.Success -> DetailContent(
            detail = current.detail,
            onBack = onBack,
        )
    }
}

@Composable
private fun DetailContent(detail: PlayerDetail, onBack: () -> Unit) {
    val player = detail.player

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
    ) {
        IconButton(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back to players",
            )
        }
        Header(player)
        Section("Season stats") { StatGrid(player) }
        Section("Fixtures") { FixtureList(detail.upcomingFixtures) }
        Section("Gameweek points") { PointsChart(detail.history) }
        Section("Past seasons") { SeasonHistoryTable(detail.pastSeasons) }
    }
}

@Composable
private fun Header(player: Player) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = player.photoUrl,
            contentDescription = player.webName,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(14.dp)),
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = player.webName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = player.team.badgeUrl,
                    contentDescription = player.team.name,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "${player.team.name} · ${player.position.label}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = player.priceLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.width(10.dp))
                StatusDot(player)
                Spacer(Modifier.width(6.dp))
                Text(
                    text = player.status.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
    if (player.status.isRisky && player.news.isNotBlank()) {
        Text(
            text = player.news,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
    }
}

@Composable
private fun StatusDot(player: Player) {
    Box(
        Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(player.status.color()),
    )
}

@Composable
private fun Section(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        content()
    }
}
