package com.fplguide.presentation.playerdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fplguide.core.common.AppError
import com.fplguide.core.common.AppResult
import com.fplguide.domain.model.PlayerDetail
import com.fplguide.domain.usecase.GetPlayerDetailUseCase
import com.fplguide.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface PlayerDetailUiState {
    data object Loading : PlayerDetailUiState
    data class Error(val error: AppError) : PlayerDetailUiState
    data class Success(val detail: PlayerDetail) : PlayerDetailUiState
}

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlayerDetail: GetPlayerDetailUseCase,
) : ViewModel() {

    private val playerId: Int = checkNotNull(savedStateHandle[Routes.ARG_PLAYER_ID])

    private val _uiState = MutableStateFlow<PlayerDetailUiState>(PlayerDetailUiState.Loading)
    val uiState: StateFlow<PlayerDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = when (val result = getPlayerDetail(playerId)) {
                is AppResult.Success -> PlayerDetailUiState.Success(result.data)
                is AppResult.Failure -> PlayerDetailUiState.Error(result.error)
            }
        }
    }

    fun retry() {
        _uiState.update { PlayerDetailUiState.Loading }
        load()
    }
}
