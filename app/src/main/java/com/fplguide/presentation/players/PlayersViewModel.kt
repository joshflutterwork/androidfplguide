package com.fplguide.presentation.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fplguide.core.common.AppError
import com.fplguide.core.common.AppResult
import com.fplguide.core.common.DispatcherProvider
import com.fplguide.domain.model.Gameweek
import com.fplguide.domain.model.Player
import com.fplguide.domain.model.PlayerQuery
import com.fplguide.domain.model.PlayerSort
import com.fplguide.domain.model.Team
import com.fplguide.domain.usecase.FilterPlayersUseCase
import com.fplguide.domain.usecase.GetCurrentGameweekUseCase
import com.fplguide.domain.usecase.GetPlayersUseCase
import com.fplguide.domain.usecase.GetTeamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlayersUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val players: List<Player> = emptyList(),
    val filtered: List<Player> = emptyList(),
    val teams: List<Team> = emptyList(),
    val query: PlayerQuery = PlayerQuery(),
    val currentGameweek: Gameweek? = null,
    val error: AppError? = null,
) {
    val hasData: Boolean get() = players.isNotEmpty()
}

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getPlayers: GetPlayersUseCase,
    private val getTeams: GetTeamsUseCase,
    private val getCurrentGameweek: GetCurrentGameweekUseCase,
    private val filterPlayers: FilterPlayersUseCase,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayersUiState())
    val uiState: StateFlow<PlayersUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        load()
    }

    /**
     * Initial load and pull-to-refresh. `forceRefresh` bypasses the bootstrap cache.
     * Load-once: returning to the players tab must never re-fetch what is already shown.
     */
    fun load(forceRefresh: Boolean = false) {
        if (!forceRefresh && _uiState.value.hasData) return
        viewModelScope.launch {
            if (forceRefresh) {
                _uiState.update { it.copy(isRefreshing = true) }
            }
            when (val result = getPlayers(forceRefresh)) {
                is AppResult.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = null,
                        players = result.data,
                        filtered = filterPlayers(result.data, it.query),
                    )
                }
                is AppResult.Failure -> _uiState.update {
                    it.copy(isLoading = false, isRefreshing = false, error = result.error)
                }
            }
            // Teams feed the filter sheet; the gameweek feeds the header chip.
            (getTeams() as? AppResult.Success)?.let { teams ->
                _uiState.update { it.copy(teams = teams.data) }
            }
            (getCurrentGameweek() as? AppResult.Success)?.let { gw ->
                _uiState.update { it.copy(currentGameweek = gw.data) }
            }
        }
    }

    /** Search is debounced; every other query change filters immediately. */
    fun onSearchChange(term: String) {
        _uiState.update { it.copy(query = it.query.copy(search = term)) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            refilter()
        }
    }

    fun onQueryChange(query: PlayerQuery) {
        _uiState.update { it.copy(query = query) }
        refilter()
    }

    fun onSortChange(sort: PlayerSort) {
        _uiState.update { it.copy(query = it.query.copy(sort = sort)) }
        refilter()
    }

    fun onToggleSortDirection() {
        _uiState.update { it.copy(query = it.query.copy(descending = !it.query.descending)) }
        refilter()
    }

    fun resetFilters() {
        val term = _uiState.value.query.search
        _uiState.update { it.copy(query = PlayerQuery(search = term)) }
        refilter()
    }

    /** ~650 rows are cheap, but still off the main thread so typing never janks. */
    private fun refilter() {
        val state = _uiState.value
        viewModelScope.launch(dispatchers.default) {
            val filtered = filterPlayers(state.players, state.query)
            _uiState.update { it.copy(filtered = filtered) }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
    }
}
