package com.example.traveldiary.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.ui.TravelDiaryRoute
import com.example.traveldiary.ui.TripsState
import com.example.traveldiary.ui.composables.AppBar
import com.example.traveldiary.ui.composables.ImageWithPlaceholder
import com.example.traveldiary.ui.composables.Size

@Composable
fun HomeScreen(state: TripsState, navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClick = { navController.navigate(TravelDiaryRoute.AddTravel) }
            ) {
                Icon(Icons.Outlined.Add, "Add Travel")
            }
        },
        topBar = { AppBar(navController, title = "TravelDiary") }
    ) { contentPadding ->
        if (state.trips.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 80.dp),
                modifier =  Modifier.padding(contentPadding)
            ) {
                items(state.trips) { item ->
                    TravelItem(
                        item,
                        onClick = { navController.navigate(TravelDiaryRoute.TravelDetails(item.id)) }
                    )
                }
            }
        } else {
            NoItemsPlaceholder(Modifier.padding(contentPadding))
        }
    }
}

@Composable
fun TravelItem(item: Trip, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .size(150.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUri = Uri.parse(item.imageUri)
            ImageWithPlaceholder(imageUri, Size.Sm)
            Spacer(Modifier.size(8.dp))
            Text(
                item.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NoItemsPlaceholder(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            Icons.Outlined.LocationOn, "Location icon",
            modifier = Modifier.padding(bottom = 16.dp).size(48.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            "No items",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Tap the + button to add a new trip.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
