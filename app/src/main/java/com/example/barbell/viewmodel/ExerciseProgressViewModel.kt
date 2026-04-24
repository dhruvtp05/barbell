package com.example.barbell.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.barbell.data.local.AppDatabase

class ExerciseProgressViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).barbellDao()

    fun exercise(exerciseId: Int) = dao.getExerciseById(exerciseId)

    fun logs(exerciseId: Int) = dao.getWorkoutLogsForExercise(exerciseId)
}
