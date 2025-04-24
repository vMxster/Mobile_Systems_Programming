package com.example.traveldiary.ui.screens.addtravel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.traveldiary.data.database.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddTravelState(
    val destination: String = "",
    val date: String = "",
    val description: String = "",
    val imageUri: Uri = Uri.EMPTY,

    val showLocationDisabledAlert: Boolean = false,
    val showLocationPermissionDeniedAlert: Boolean = false,
    val showLocationPermissionPermanentlyDeniedSnackbar: Boolean = false,
    val showNoInternetConnectivitySnackbar: Boolean = false
) {
    val canSubmit get() = destination.isNotBlank() && date.isNotBlank() && description.isNotBlank()

    fun toTrip() = Trip(
        name = destination,
        description =  description,
        date = date,
        imageUri = imageUri.toString()
    )
}

interface AddTravelActions {
    fun setDestination(destination: String)
    fun setDate(date: String)
    fun setDescription(description: String)
    fun setImageUri(imageUri: Uri)

    fun setShowLocationDisabledAlert(show: Boolean)
    fun setShowLocationPermissionDeniedAlert(show: Boolean)
    fun setShowLocationPermissionPermanentlyDeniedSnackbar(show: Boolean)
    fun setShowNoInternetConnectivitySnackbar(show: Boolean)
}

class AddTravelViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddTravelState())
    val state = _state.asStateFlow()

    val actions = object : AddTravelActions {
        override fun setDestination(destination: String) =
            _state.update { it.copy(destination = destination) }

        override fun setDate(date: String) =
            _state.update { it.copy(date = date) }

        override fun setDescription(description: String) =
            _state.update { it.copy(description = description) }

        override fun setImageUri(imageUri: Uri) =
            _state.update { it.copy(imageUri = imageUri) }

        override fun setShowLocationDisabledAlert(show: Boolean) =
            _state.update { it.copy(showLocationDisabledAlert = show) }

        override fun setShowLocationPermissionDeniedAlert(show: Boolean) =
            _state.update { it.copy(showLocationPermissionDeniedAlert = show) }

        override fun setShowLocationPermissionPermanentlyDeniedSnackbar(show: Boolean) =
            _state.update { it.copy(showLocationPermissionPermanentlyDeniedSnackbar = show) }

        override fun setShowNoInternetConnectivitySnackbar(show: Boolean) =
            _state.update { it.copy(showNoInternetConnectivitySnackbar = show) }
    }
}
