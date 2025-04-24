package com.example.traveldiary.ui.screens.traveldetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.ui.composables.AppBar
import com.example.traveldiary.ui.composables.ImageWithPlaceholder
import com.example.traveldiary.ui.composables.Size

@Composable
fun TravelDetailsScreen(trip: Trip, navController: NavController) {
    val ctx = LocalContext.current

    fun shareDetails() {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, trip.name)
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Travel")
        if (shareIntent.resolveActivity(ctx.packageManager) != null) {
            ctx.startActivity(shareIntent)
        }
    }

    Scaffold(
        topBar = { AppBar(navController, title = "Travel Details") },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClick = ::shareDetails
            ) {
                Icon(Icons.Outlined.Share, "Share Travel")
            }
        },
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(contentPadding).padding(12.dp).fillMaxSize()
        ) {
            val imageUri = Uri.parse(trip.imageUri)
            ImageWithPlaceholder(imageUri, Size.Lg)
            Text(
                trip.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                trip.date,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.size(8.dp))
            Text(
                trip.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
