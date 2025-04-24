package com.example.traveldiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.traveldiary.ui.screens.addtravel.AddTravelScreen
import com.example.traveldiary.ui.screens.addtravel.AddTravelViewModel
import com.example.traveldiary.ui.screens.home.HomeScreen
import com.example.traveldiary.ui.screens.settings.SettingsScreen
import com.example.traveldiary.ui.screens.settings.SettingsViewModel
import com.example.traveldiary.ui.screens.traveldetails.TravelDetailsScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

sealed interface TravelDiaryRoute {
    @Serializable data object Home : TravelDiaryRoute
    @Serializable data class TravelDetails(val travelId: Int) : TravelDiaryRoute
    @Serializable data object AddTravel : TravelDiaryRoute
    @Serializable data object Settings : TravelDiaryRoute
}

@Composable
fun TravelDiaryNavGraph(navController: NavHostController) {
    val tripsVm = koinViewModel<TripsViewModel>()
    val tripsState by tripsVm.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = TravelDiaryRoute.Home
    ) {
        composable<TravelDiaryRoute.Home> {
            HomeScreen(tripsState, navController)
        }
        composable<TravelDiaryRoute.TravelDetails> { backStackEntry ->
            val route = backStackEntry.toRoute<TravelDiaryRoute.TravelDetails>()
            val trip = requireNotNull(tripsState.trips.find { it.id == route.travelId })
            TravelDetailsScreen(trip, navController)
        }
        composable<TravelDiaryRoute.AddTravel> {
            val addTravelVm = koinViewModel<AddTravelViewModel>()
            val state by addTravelVm.state.collectAsStateWithLifecycle()
            AddTravelScreen(
                state,
                addTravelVm.actions,
                onSubmit = { tripsVm.addTrip(state.toTrip()) },
                navController
            )
        }
        composable<TravelDiaryRoute.Settings> {
            val settingsVm = koinViewModel<SettingsViewModel>()
            SettingsScreen(settingsVm.state, settingsVm::setUsername, navController)
        }
    }
}
