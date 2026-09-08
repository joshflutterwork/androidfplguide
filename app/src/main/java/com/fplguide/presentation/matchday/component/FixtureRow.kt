package com.fplguide.presentation.matchday.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.core.designsystem.FplPurpleSurface
import com.fplguide.core.designsystem.fdrColor
import com.fplguide.core.util.formatKickoff
import com.fplguide.domain.model.Fixture
import com.fplguide.domain.model.Team

/**
 * One fixture per requirements.md §6.1 anatomy: badges on the outer edges, FDR chips
 * flanking the centre, score (or kickoff) as the single focal point. Live matches are
 * the only green element in the row.
 */
@Composable
fun FixtureRow(
    fixture: Fixture,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(FplPurpleSurface)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TeamSide(team = fixture.home, modifier = Modifier.weight(1f))
        Spacer(Modifier.width(8.dp))
        FdrChip(fixture.homeDifficulty)
        Spacer(Modifier.width(12.dp))
        ScoreBlock(fixture)
        Spacer(Modifier.width(12.dp))
        FdrChip(fixture.awayDifficulty)
        Spacer(Modifier.width(8.dp))
        TeamSide(team = fixture.away, endAligned = true, modifier = Modifier.weight(1f))
    }
}

/** Badge + short name; mirrored for the away side so badges hug the row edges. */
@Composable
private fun TeamSide(
    team: Team,
    endAligned: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!endAligned) {
            TeamBadge(team)
            Spacer(Modifier.width(8.dp))
        }
        Text(
            text = team.shortName.uppercase(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (endAligned) {
            Spacer(Modifier.width(8.dp))
            TeamBadge(team)
        }
    }
}

@Composable
private fun TeamBadge(team: Team) {
    AsyncImage(
        model = team.badgeUrl,
        contentDescription = team.shortName,
        modifier = Modifier.size(28.dp),
    )
}

@Composable
private fun FdrChip(difficulty: Int) {
    Text(
        text = difficulty.toString(),
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(fdrColor(difficulty))
            .padding(horizontal = 6.dp, vertical = 2.dp),
    )
}

/** Score when played, kickoff when not; live state is the scarce green accent. */
@Composable
private fun ScoreBlock(fixture: Fixture) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = fixture.scoreLabel,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (fixture.isLive) FplGreen else MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = when {
                fixture.finished -> "FT"
                fixture.isLive -> "LIVE ${fixture.minutes}'"
                else -> formatKickoff(fixture.kickoffTime)
            },
            style = MaterialTheme.typography.bodySmall,
            color = if (fixture.isLive) FplGreen else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
