package com.example.barbell.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.barbell.data.model.Exercise
import com.example.barbell.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbellDao {
    // Get our list of exercises to show up on the Home Screen
    @Query("SELECT * FROM exercises")
    fun getAllExercises(): Flow<List<Exercise>>

    // Saves a new exercise
    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Insert
    suspend fun insertWorkoutLog(log: WorkoutLog)
}