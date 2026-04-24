package com.example.barbell.ui.screens.exercise_progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.barbell.shared.domain.ProgressiveOverloadCalculator
import com.example.barbell.viewmodel.ExerciseProgressViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseProgressScreen(
    exerciseId: String?,
    onNavigateBack: () -> Unit,
    onAddEntry: (String) -> Unit,
    viewModel: ExerciseProgressViewModel = viewModel()
) {
    val parsedId = exerciseId?.toIntOrNull() ?: 0
    val exercise by viewModel.exercise(parsedId).collectAsState(initial = null)
    val logs by viewModel.logs(parsedId).collectAsState(initial = emptyList())
    val bestWeight = logs.maxOfOrNull { it.weight } ?: 0f
    val overload = ProgressiveOverloadCalculator.fromWeightsDesc(logs.map { it.weight })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(exercise?.name ?: "Exercise Progress")
                        Text(
                            text = "Progress timeline",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Body Area: ${exercise?.muscleGroup ?: "Unknown"}",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Sessions", style = MaterialTheme.typography.labelMedium)
                        Text(
                            logs.size.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Best Weight", style = MaterialTheme.typography.labelMedium)
                        Text(
                            "${bestWeight.roundToInt()}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Progressive Overload", style = MaterialTheme.typography.labelMedium)
                        Text(
                            overload.formattedLabel(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "${overload.startWeight.roundToInt()} -> ${overload.latestWeight.roundToInt()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Button(
                onClick = { onAddEntry(parsedId.toString()) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add New Entry")
            }
            if (logs.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Text(
                        text = "No entries yet. Add your first set!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(logs) { log ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "${log.weight} x ${log.reps} reps x ${log.sets} sets",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = formatTimestamp(log.timestamp),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                if (!log.equipmentPhotoUri.isNullOrBlank()) {
                                    AsyncImage(
                                        model = log.equipmentPhotoUri,
                                        contentDescription = "Attached equipment photo",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                            .padding(top = 8.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
