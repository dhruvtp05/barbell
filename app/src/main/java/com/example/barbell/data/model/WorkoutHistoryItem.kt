package com.example.barbell.data.model

data class WorkoutHistoryItem(
    val id: Int,
    val exerciseId: Int,
    val exerciseName: String,
    val muscleGroup: String,
    val weight: Float,
    val reps: Int,
    val sets: Int,
    val equipmentPhotoUri: String?,
    val timestamp: Long
)
