package com.example.barbell.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbell.data.local.AppDatabase
import com.example.barbell.data.model.Exercise
import com.example.barbell.shared.domain.MuscleGroup
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).barbellDao()

    val exercises: StateFlow<List<Exercise>> = dao.getAllExercises().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        seedPresetExercisesIfNeeded()
    }

    private fun seedPresetExercisesIfNeeded() {
        viewModelScope.launch {
            if (dao.getAllExercisesOnce().isNotEmpty()) return@launch

            val presets = listOf(
                Exercise(name = "Barbell Bench Press", muscleGroup = "Chest", isPreset = true),
                Exercise(name = "Barbell Back Squat", muscleGroup = "Legs", isPreset = true),
                Exercise(name = "Barbell Deadlift", muscleGroup = "Back", isPreset = true)
            )
            presets.forEach { dao.insertExercise(it) }
        }
    }

    fun addCustomExercise(name: String, muscleGroup: String) {
        val trimmedName = name.trim()
        val trimmedMuscleGroup = MuscleGroup.normalize(muscleGroup)
        if (trimmedName.isEmpty() || trimmedMuscleGroup.isEmpty()) return

        viewModelScope.launch {
            dao.insertExercise(
                Exercise(
                    name = trimmedName,
                    muscleGroup = trimmedMuscleGroup,
                    isPreset = false
                )
            )
        }
    }
}