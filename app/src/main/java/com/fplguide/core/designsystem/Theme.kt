package com.fplguide.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

/**
 * App-wide Compose theme.
 *
 * The app is deliberately dark-only. The FPL brand palette in [Color.kt] is a purple/green
 * system that only resolves on a dark ground, and the screens paint brand surfaces
 * (`FplPurpleSurface`, `FplPurpleSurfaceDark`) directly rather than through scheme roles.
 * Following the system setting therefore produced a broken hybrid — light-scheme `onSurface`
 * (near-black) text drawn on hardcoded dark-purple cards, which is unreadable.
 *
 * So: one scheme, always. If a light theme is wanted later it has to come with the wider
 * refactor of routing every brand colour through [MaterialTheme.colorScheme] — tracked in
 * `additional_requirements.md`.
 */
@Composable
fun FplGuideTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = FplTypography,
        content = {
            Surface(content = content)
        },
    )
}
