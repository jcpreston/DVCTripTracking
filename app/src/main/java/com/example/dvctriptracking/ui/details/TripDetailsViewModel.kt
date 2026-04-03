package com.example.dvctriptracking.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dvctriptracking.data.Trip
import com.example.dvctriptracking.data.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TripDetailsViewModel(
    private val repository: TripRepository,
    private val tripId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow<TripDetailsUiState>(TripDetailsUiState.Loading)
    val uiState: StateFlow<TripDetailsUiState> = _uiState.asStateFlow()

    init {
        loadTrip()
    }

    private fun loadTrip() {
        viewModelScope.launch {
            val trip = repository.getTripById(tripId)
            if (trip != null) {
                _uiState.value = TripDetailsUiState.Success(trip)
            } else {
                _uiState.value = TripDetailsUiState.Error("Trip not found")
            }
        }
    }

    fun deleteTrip(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is TripDetailsUiState.Success) {
            viewModelScope.launch {
                repository.delete(currentState.trip)
                onSuccess()
            }
        }
    }
}

sealed class TripDetailsUiState {
    object Loading : TripDetailsUiState()
    data class Success(val trip: Trip) : TripDetailsUiState()
    data class Error(val message: String) : TripDetailsUiState()
}

class TripDetailsViewModelFactory(
    private val repository: TripRepository,
    private val tripId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TripDetailsViewModel(repository, tripId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
