package com.example.barbell.shared.domain

enum class MuscleGroup(val label: String) {
    CHEST("Chest"),
    BICEPS("Biceps"),
    SHOULDERS("Shoulders"),
    TRICEPS("Triceps"),
    LEGS("Legs"),
    ABS("Abs"),
    BACK("Back"),
    FOREARMS("Forearms");

    companion object {
        fun labels(): List<String> = entries.map { it.label }

        fun normalize(input: String): String {
            val match = entries.firstOrNull { it.label.equals(input.trim(), ignoreCase = true) }
            return match?.label ?: input.trim()
        }
    }
}
