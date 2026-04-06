package com.example.barbell.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.barbell.data.model.Exercise
import com.example.barbell.data.model.WorkoutLog

@Database(entities = [Exercise::class, WorkoutLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun barbellDao(): BarbellDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "barbell_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}