package com.example.dvctriptracking.ui.addtrip

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dvctriptracking.data.Trip
import com.example.dvctriptracking.data.TripRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class AddTripViewModel(private val repository: TripRepository) : ViewModel() {

    var resortName by mutableStateOf("")
        private set

    var checkInDate by mutableStateOf<LocalDate?>(null)
        private set

    var checkOutDate by mutableStateOf<LocalDate?>(null)
        private set

    var notes by mutableStateOf("")
        private set

    val durationDays: Int
        get() {
            val start = checkInDate
            val end = checkOutDate
            return if (start != null && end != null) {
                ChronoUnit.DAYS.between(start, end).toInt()
            } else {
                0
            }
        }

    fun updateResortName(name: String) {
        resortName = name
    }

    fun updateDateRange(start: LocalDate?, end: LocalDate?) {
        checkInDate = start
        checkOutDate = end
    }

    fun updateNotes(newNotes: String) {
        notes = newNotes
    }

    fun saveTrip(onSuccess: () -> Unit) {
        val start = checkInDate ?: return
        val duration = durationDays
        if (duration <= 0) return

        val trip = Trip(
            resortName = resortName,
            checkInDate = start,
            durationDays = duration,
            notes = notes
        )
        viewModelScope.launch {
            repository.insert(trip)
            onSuccess()
        }
    }
}

class AddTripViewModelFactory(private val repository: TripRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTripViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTripViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
