package com.example.barbell.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.barbell.data.model.Exercise
import com.example.barbell.data.model.WorkoutHistoryItem
import com.example.barbell.data.model.WorkoutLog
import kotlinx.coroutines.flow.Flow

@Dao
interface BarbellDao {
    // Get our list of exercises to show up on the Home Screen
    @Query("SELECT * FROM exercises")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercisesOnce(): List<Exercise>

    // Saves a new exercise
    @Insert
    suspend fun insertExercise(exercise: Exercise): Long

    @Insert
    suspend fun insertWorkoutLog(log: WorkoutLog)

    @Query("SELECT * FROM exercises WHERE id = :exerciseId LIMIT 1")
    fun getExerciseById(exerciseId: Int): Flow<Exercise?>

    @Query("SELECT * FROM workout_logs WHERE exerciseId = :exerciseId ORDER BY timestamp DESC")
    fun getWorkoutLogsForExercise(exerciseId: Int): Flow<List<WorkoutLog>>

    @Query(
        """
        SELECT
            workout_logs.id AS id,
            workout_logs.exerciseId AS exerciseId,
            exercises.name AS exerciseName,
            exercises.muscleGroup AS muscleGroup,
            workout_logs.weight AS weight,
            workout_logs.reps AS reps,
            workout_logs.sets AS sets,
            workout_logs.equipmentPhotoUri AS equipmentPhotoUri,
            workout_logs.timestamp AS timestamp
        FROM workout_logs
        INNER JOIN exercises ON exercises.id = workout_logs.exerciseId
        ORDER BY workout_logs.timestamp DESC
        """
    )
    fun getWorkoutHistory(): Flow<List<WorkoutHistoryItem>>
}