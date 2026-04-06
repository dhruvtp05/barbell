package com.example.barbell.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbell.data.local.AppDatabase
import com.example.barbell.data.model.WorkoutLog
import kotlinx.coroutines.launch

class LogWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    // Connect to our database
    private val dao = AppDatabase.getDatabase(application).barbellDao()

    fun saveWorkout(
        exerciseId: String,
        weight: String,
        sets: String,
        reps: String,
        photoUri: String?
    ) {
        // Convert the Strings from the text fields into actual numbers
        val weightFloat = weight.toFloatOrNull() ?: 0f
        val setsInt = sets.toIntOrNull() ?: 0
        val repsInt = reps.toIntOrNull() ?: 0
        val exerciseIdInt = exerciseId.toIntOrNull() ?: 0

        // Create the log and send it to Room!
        val newLog = WorkoutLog(
            exerciseId = exerciseIdInt,
            weight = weightFloat,
            sets = setsInt,
            reps = repsInt,
            equipmentPhotoUri = photoUri
        )

        viewModelScope.launch {
            dao.insertWorkoutLog(newLog)
        }
    }
}