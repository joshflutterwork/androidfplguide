package com.fplguide.core.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/** `1.8` -> "1.8", `25.0` -> "25.0". Locale-stable for stat columns. */
fun Double.formatDecimal(digits: Int = 1): String = "%.${digits}f".format(this)

/**
 * The FPL API ships numeric stats as strings, and occasionally as `""` or `"None"`.
 * All of them degrade to `0.0` here so a schema hiccup can never crash a mapper.
 */
fun String?.toDoubleOrZero(): Double = this?.trim()?.toDoubleOrNull() ?: 0.0

/**
 * ISO-8601 UTC kickoff ("2026-09-13T14:00:00Z") -> device-local "Sat 14 Sep, 20:00".
 * Null or malformed input means the fixture is unscheduled: "TBD".
 */
fun formatKickoff(iso: String?): String {
    if (iso.isNullOrBlank()) return "TBD"
    return runCatching {
        OffsetDateTime.parse(iso)
            .format(DateTimeFormatter.ofPattern("EEE d MMM, HH:mm"))
    }.getOrDefault("TBD")
}
