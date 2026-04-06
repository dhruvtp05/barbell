package com.example.barbell.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_logs")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: Int,          // Links back to the Exercise
    val weight: Float,
    val reps: Int,
    val sets: Int,
    val equipmentPhotoUri: String?, // The local file path to the picture taken
    val timestamp: Long = System.currentTimeMillis()
)