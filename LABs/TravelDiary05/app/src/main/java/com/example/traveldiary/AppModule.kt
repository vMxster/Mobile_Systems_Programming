package com.example.traveldiary

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.traveldiary.data.database.TravelDiaryDatabase
import com.example.traveldiary.data.remote.OSMDataSource
import com.example.traveldiary.data.repositories.TripsRepository
import com.example.traveldiary.data.repositories.SettingsRepository
import com.example.traveldiary.ui.TripsViewModel
import com.example.traveldiary.ui.screens.addtravel.AddTravelViewModel
import com.example.traveldiary.ui.screens.settings.SettingsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("settings")

val appModule = module {
    single { get<Context>().dataStore }

    single {
        Room.databaseBuilder(
            get(),
            TravelDiaryDatabase::class.java,
            "travel-diary"
        )
            // Sconsigliato per progetti seri! Lo usiamo solo qui per semplicità
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single { OSMDataSource(get()) }

    single { SettingsRepository(get()) }

    single {
        TripsRepository(get<TravelDiaryDatabase>().tripsDAO(), get<Context>().contentResolver)
    }

    viewModel { AddTravelViewModel() }

    viewModel { SettingsViewModel(get()) }

    viewModel { TripsViewModel(get()) }
}
