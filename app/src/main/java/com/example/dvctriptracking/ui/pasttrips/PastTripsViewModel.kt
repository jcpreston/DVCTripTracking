package com.example.dvctriptracking.ui.pasttrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dvctriptracking.data.Trip
import com.example.dvctriptracking.data.TripRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class PastTripsViewModel(private val repository: TripRepository) : ViewModel() {

    val uiState: StateFlow<PastTripsUiState> = repository.allTrips
        .map { trips ->
            val today = LocalDate.now()
            val pastTrips = trips.filter { it.checkInDate < today }
                .sortedByDescending { it.checkInDate }
            PastTripsUiState(trips = pastTrips)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PastTripsUiState(isLoading = true)
        )
}

data class PastTripsUiState(
    val trips: List<Trip> = emptyList(),
    val isLoading: Boolean = false
)

class PastTripsViewModelFactory(private val repository: TripRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PastTripsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PastTripsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
