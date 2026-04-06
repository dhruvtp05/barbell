package com.example.barbell.viewmodel

import androidx.lifecycle.ViewModel
import com.example.barbell.data.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _exercises = MutableStateFlow(
        listOf(
            Exercise(id = 1, name = "Barbell Bench Press", isPreset = true),
            Exercise(id = 2, name = "Barbell Back Squat", isPreset = true),
            Exercise(id = 3, name = "Barbell Deadlift")


        )
    )
    val exercises: StateFlow<List<Exercise>> = _exercises
}