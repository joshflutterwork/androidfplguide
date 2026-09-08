package com.fplguide.domain.model

data class Gameweek(
    val id: Int,
    val name: String,
    val deadlineTime: String,
    val finished: Boolean,
    val isCurrent: Boolean,
    val isNext: Boolean,
    val averageEntryScore: Int,
    val highestScore: Int?,
)
