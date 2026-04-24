package com.example.barbell.shared.domain

import kotlin.math.abs
import kotlin.math.roundToInt

data class ProgressiveOverloadResult(
    val percentChange: Float,
    val startWeight: Float,
    val latestWeight: Float,
    val hasEnoughData: Boolean
) {
    fun formattedLabel(): String {
        if (!hasEnoughData) return "Not enough data"
        return when {
            percentChange > 0f -> "+${percentChange.roundToInt()}%"
            percentChange < 0f -> "-${abs(percentChange).roundToInt()}%"
            else -> "0%"
        }
    }
}

object ProgressiveOverloadCalculator {
    fun fromWeightsDesc(weightsByNewestFirst: List<Float>): ProgressiveOverloadResult {
        val latestWeight = weightsByNewestFirst.firstOrNull() ?: 0f
        val startWeight = weightsByNewestFirst.lastOrNull() ?: 0f
        val enoughData = weightsByNewestFirst.size >= 2 && startWeight > 0f
        val percent = if (enoughData) ((latestWeight - startWeight) / startWeight) * 100f else 0f
        return ProgressiveOverloadResult(
            percentChange = percent,
            startWeight = startWeight,
            latestWeight = latestWeight,
            hasEnoughData = enoughData
        )
    }
}
