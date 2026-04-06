package com.example.barbell.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "exercises")
data class Exercise (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isPreset: Boolean = false   // True for standard exercises
)