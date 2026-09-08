package com.fplguide.core.designsystem

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.fplguide.R

/**
 * Bundled Google Fonts (SIL OFL — license texts live in `/licenses`).
 *
 * These ship as static instances subset to Latin/Latin-Ext rather than going through
 * `androidx.compose.ui:ui-text-google-fonts`. Downloadable fonts resolve via the Google Play
 * Services font provider, which means they silently fall back to Roboto on any device or
 * emulator image without GMS — unacceptable for a design where type carries the hierarchy.
 * Bundling costs ~620 KB and renders identically everywhere, on the first frame, offline.
 *
 * - [Outfit] — geometric sans for headings and hero numbers; gives the app its voice.
 * - [Inter]  — UI/body workhorse, designed for small sizes, with real tabular figures.
 */
val Outfit = FontFamily(
    Font(R.font.outfit_semibold, FontWeight.SemiBold),
    Font(R.font.outfit_bold, FontWeight.Bold),
)

val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

/**
 * OpenType `tnum`: every digit occupies the same advance width, so numbers in a column
 * line up and a changing score doesn't make the row jitter. Applied to every stat value.
 */
const val TabularFigures = "tnum"
