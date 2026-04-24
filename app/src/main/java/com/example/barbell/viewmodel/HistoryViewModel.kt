package com.example.barbell.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbell.data.local.AppDatabase
import com.example.barbell.data.model.WorkoutHistoryItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).barbellDao()

    val groupedHistory: StateFlow<Map<String, List<WorkoutHistoryItem>>> =
        dao.getWorkoutHistory()
            .map { entries -> entries.groupBy { it.muscleGroup } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyMap()
            )
}