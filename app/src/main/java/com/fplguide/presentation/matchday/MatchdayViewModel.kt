package com.fplguide.presentation.matchday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fplguide.core.common.AppError
import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.Fixture
import com.fplguide.domain.usecase.GetCurrentGameweekUseCase
import com.fplguide.domain.usecase.GetFixturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatchdayUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val fixtures: List<Fixture> = emptyList(),
    val selectedGameweek: Int = 0,
    val currentGameweek: Int? = null,
    val error: AppError? = null,
) {
    val hasData: Boolean get() = fixtures.isNotEmpty()

    /** Highest gameweek present in the payload — the season length (38) without hardcoding. */
    val lastGameweek: Int get() = fixtures.maxOfOrNull { it.event } ?: 38

    val fixturesForSelectedWeek: List<Fixture>
        get() = fixtures
            .filter { it.event == selectedGameweek }
            .sortedBy { it.kickoffTime.orEmpty() }

    val canGoPrevious: Boolean get() = selectedGameweek > 1
    val canGoNext: Boolean get() = selectedGameweek < lastGameweek

    /** Green marker when browsing the live gameweek. */
    val isViewingCurrentWeek: Boolean get() = currentGameweek != null && selectedGameweek == currentGameweek
}

@HiltViewModel
class MatchdayViewModel @Inject constructor(
    private val getFixtures: GetFixturesUseCase,
    private val getCurrentGameweek: GetCurrentGameweekUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchdayUiState())
    val uiState: StateFlow<MatchdayUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    /**
     * Initial load and pull-to-refresh. Load-once: once the season list is in state,
     * revisiting the matchday tab (or flipping gameweeks) never re-fetches — the whole
     * season arrives in one payload and weeks are sliced client-side.
     */
    fun load(forceRefresh: Boolean = false) {
        if (!forceRefresh && _uiState.value.hasData) return
        viewModelScope.launch {
            if (forceRefresh) {
                _uiState.update { it.copy(isRefreshing = true) }
            }
            when (val result = getFixtures(forceRefresh)) {
                is AppResult.Success -> {
                    // Bootstrap-served value; cached, so this is free after the players tab.
                    val currentGameweek = (getCurrentGameweek() as? AppResult.Success)?.data?.id
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = null,
                            fixtures = result.data,
                            currentGameweek = currentGameweek,
                            selectedGameweek = state.selectedGameweek.takeIf { it > 0 }
                                ?: (currentGameweek ?: 1),
                        )
                    }
                }
                is AppResult.Failure -> _uiState.update {
                    it.copy(isLoading = false, isRefreshing = false, error = result.error)
                }
            }
        }
    }

    /** ‹ / › stepper, clamped to the season boundaries. Pure state change — no network. */
    fun changeGameweek(delta: Int) {
        _uiState.update { state ->
            val target = (state.selectedGameweek + delta).coerceIn(1, state.lastGameweek)
            if (target == state.selectedGameweek) state else state.copy(selectedGameweek = target)
        }
    }

    /** Jump straight back to the live gameweek. */
    fun jumpToCurrentGameweek() {
        val current = _uiState.value.currentGameweek ?: return
        _uiState.update { it.copy(selectedGameweek = current) }
    }
}
