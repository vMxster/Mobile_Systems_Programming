package com.example.traveldiary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.data.repositories.TripsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TripsState(val trips: List<Trip>)

class TripsViewModel(
    private val repository: TripsRepository
) : ViewModel() {
    val state = repository.trips.map { TripsState(trips = it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TripsState(emptyList())
    )

    fun addTrip(trip: Trip) = viewModelScope.launch {
        repository.upsert(trip)
    }

    fun deleteTrip(trip: Trip) = viewModelScope.launch {
        repository.delete(trip)
    }
}
