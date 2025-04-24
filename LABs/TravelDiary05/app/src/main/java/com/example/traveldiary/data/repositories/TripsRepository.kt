package com.example.traveldiary.data.repositories

import android.content.ContentResolver
import android.net.Uri
import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.data.database.TripsDAO
import com.example.traveldiary.utils.saveImageToStorage
import kotlinx.coroutines.flow.Flow

class TripsRepository(
    private val dao: TripsDAO,
    private val contentResolver: ContentResolver
) {
    val trips: Flow<List<Trip>> = dao.getAll()

    suspend fun upsert(trip: Trip) {
        if (trip.imageUri?.isNotEmpty() == true) {
            val imageUri = saveImageToStorage(
                Uri.parse(trip.imageUri),
                contentResolver,
                "TravelDiary_Trip${trip.name}"
            )
            dao.upsert(trip.copy(imageUri = imageUri.toString()))
        } else {
            dao.upsert(trip)
        }
    }

    suspend fun delete(trip: Trip) = dao.delete(trip)
}
