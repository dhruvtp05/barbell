package com.example.barbell.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbell.shared.domain.MuscleGroup
import com.example.barbell.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToExerciseProgress: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val muscleGroupOptions = MuscleGroup.labels()

    // This grabs our list of exercises from the ViewModel
    val exercises by viewModel.exercises.collectAsState()
    val groupedExercises = remember(exercises) {
        muscleGroupOptions.associateWith { group ->
            exercises.filter { it.muscleGroup.equals(group, ignoreCase = true) }
        }
    }
    val activeGroupsCount = groupedExercises.values.count { it.isNotEmpty() }
    var showAddExerciseDialog by remember { mutableStateOf(false) }
    var exerciseName by remember { mutableStateOf("") }
    var bodyArea by remember { mutableStateOf("") }
    var bodyAreaDropdownExpanded by remember { mutableStateOf(false) }

    if (showAddExerciseDialog) {
        AlertDialog(
            onDismissRequest = { showAddExerciseDialog = false },
            title = { Text("New Exercise") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = exerciseName,
                        onValueChange = { exerciseName = it },
                        label = { Text("Exercise name") },
                        singleLine = true
                    )
                    ExposedDropdownMenuBox(
                        expanded = bodyAreaDropdownExpanded,
                        onExpandedChange = { bodyAreaDropdownExpanded = !bodyAreaDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = bodyArea,
                            onValueChange = {},
                            label = { Text("Body area") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select body area"
                                )
                            },
                            singleLine = true
                        )
                        ExposedDropdownMenu(
                            expanded = bodyAreaDropdownExpanded,
                            onDismissRequest = { bodyAreaDropdownExpanded = false }
                        ) {
                            muscleGroupOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        bodyArea = option
                                        bodyAreaDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                val canSave = exerciseName.isNotBlank() && bodyArea.isNotBlank()
                TextButton(
                    onClick = {
                        viewModel.addCustomExercise(exerciseName, bodyArea)
                        exerciseName = ""
                        bodyArea = ""
                        bodyAreaDropdownExpanded = false
                        showAddExerciseDialog = false
                    },
                    enabled = canSave
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddExerciseDialog = false
                        exerciseName = ""
                        bodyArea = ""
                        bodyAreaDropdownExpanded = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("My Exercises", fontWeight = FontWeight.SemiBold)
                        Text(
                            text = "Track progress by body area",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showAddExerciseDialog = true }) {
                Text("Add Exercise")
            }
        }
    ) { paddingValues ->
        if (exercises.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("No exercises yet", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Tap Add Exercise to create your first exercise.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Total Exercises", style = MaterialTheme.typography.labelMedium)
                                Text(
                                    exercises.size.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column {
                                Text("Active Groups", style = MaterialTheme.typography.labelMedium)
                                Text(
                                    activeGroupsCount.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                muscleGroupOptions.forEach { group ->
                    val itemsForGroup = groupedExercises[group].orEmpty()
                    if (itemsForGroup.isNotEmpty()) {
                        item {
                            Text(
                                text = group,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                            )
                        }
                        items(itemsForGroup) { exercise ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigateToExerciseProgress(exercise.id.toString()) },
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    AssistChip(
                                        onClick = {},
                                        enabled = false,
                                        label = { Text(group) }
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