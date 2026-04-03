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

class AddTripViewModel(private val repository: TripRepository) : ViewModel() {

    var resortName by mutableStateOf("")
        private set

    var checkInDate by mutableStateOf<LocalDate>(LocalDate.now())
        private set

    var durationDays by mutableStateOf("7")
        private set

    var notes by mutableStateOf("")
        private set

    fun updateResortName(name: String) {
        resortName = name
    }

    fun updateCheckInDate(date: LocalDate) {
        checkInDate = date
    }

    fun updateDuration(duration: String) {
        if (duration.all { it.isDigit() }) {
            durationDays = duration
        }
    }

    fun updateNotes(newNotes: String) {
        notes = newNotes
    }

    fun saveTrip(onSuccess: () -> Unit) {
        val duration = durationDays.toIntOrNull() ?: 7
        val trip = Trip(
            resortName = resortName,
            checkInDate = checkInDate,
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
