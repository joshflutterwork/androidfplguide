package com.fplguide.core.designsystem

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.fplguide.domain.model.PlayerStatus
import com.fplguide.domain.model.Position

// ── Brand palette (visual reference: inspiration.webp, spec requirements.md §6.1) ──

val FplPurple = Color(0xFF37003C) // app background
val FplGreen = Color(0xFF00FF87) // scarce accent: active chips, sort stat, CTAs
val FplOnGreen = Color(0xFF00321C) // text/icons sitting on a green fill
val FplPurpleSurface = Color(0xFF4A004E) // rows / cards
val FplPurpleSurfaceDark = Color(0xFF1A051B) // secondary surfaces, pills
val FplTextLight = Color(0xFFF5F5F5) // near-white text — never pure white
val FplTextDim = Color(0xFFB8A2BC) // secondary text on purple
val FplGreenDim = Color(0xFF04BF7A) // green that passes contrast as text on purple
val FplPriceDown = Color(0xFFEB5757) // price fell this gameweek
// Hairline on row cards. FplPurpleSurface sits only ~3% off the background, so without this
// edge the "cards" read as one undifferentiated purple slab.
val FplRowBorder = Color(0x14FFFFFF)
val FplDivider = Color(0x0FFFFFFF)

// ── Position badges (list dots + detail chips) ──

val GkpColor = Color(0xFFF5A623)
val DefColor = Color(0xFF2D9CDB)
val MidColor = Color(0xFF27AE60)
val FwdColor = Color(0xFFEB5757)

fun Position.color(): Color = when (this) {
    Position.GOALKEEPER -> GkpColor
    Position.DEFENDER -> DefColor
    Position.MIDFIELDER -> MidColor
    Position.FORWARD -> FwdColor
}

// ── Availability status ──

val StatusAvailable = FplGreenDim
val StatusDoubtful = Color(0xFFF5A623)
val StatusUnavailable = Color(0xFFEB5757)
val StatusNotInSquad = Color(0xFF828282)

fun PlayerStatus.color(): Color = when (this) {
    PlayerStatus.AVAILABLE -> StatusAvailable
    PlayerStatus.DOUBTFUL -> StatusDoubtful
    PlayerStatus.INJURED, PlayerStatus.SUSPENDED, PlayerStatus.UNAVAILABLE -> StatusUnavailable
    PlayerStatus.NOT_IN_SQUAD -> StatusNotInSquad
}

// ── FDR difficulty 1 (easy) → 5 (hard) ──

val FdrColors = listOf(
    Color(0xFF1F8F4D), // 1
    Color(0xFF4CAF50), // 2
    Color(0xFF9E9E9E), // 3
    Color(0xFFF2994A), // 4
    Color(0xFFEB5757), // 5
)

fun fdrColor(difficulty: Int): Color =
    FdrColors.getOrElse(difficulty - 1) { FdrColors[2] }

// ── M3 schemes: dark-first, matching the FPL app's own dark language ──

val DarkColorScheme = darkColorScheme(
    primary = FplGreen,
    onPrimary = Color(0xFF00321C),
    secondary = FplGreenDim,
    onSecondary = Color(0xFF00321C),
    tertiary = Color(0xFFB57EDC),
    background = FplPurple,
    onBackground = FplTextLight,
    surface = FplPurple,
    onSurface = FplTextLight,
    surfaceVariant = FplPurpleSurface,
    onSurfaceVariant = FplTextDim,
    surfaceContainer = FplPurpleSurface,
    surfaceContainerHigh = FplPurpleSurface,
    error = Color(0xFFFF6B6B),
    onError = Color(0xFF2C0000),
    outline = Color(0xFF7A5A80),
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF04BF7A),
    onPrimary = Color.White,
    secondary = Color(0xFF37003C),
    onSecondary = Color.White,
    tertiary = Color(0xFF6A0F73),
    background = Color(0xFFF7F3F8),
    onBackground = Color(0xFF1F0A22),
    surface = Color.White,
    onSurface = Color(0xFF1F0A22),
    surfaceVariant = Color(0xFFEFE6F0),
    onSurfaceVariant = Color(0xFF5D4A61),
    error = Color(0xFFB3261E),
)
