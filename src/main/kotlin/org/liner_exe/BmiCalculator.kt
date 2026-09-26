package org.liner_exe

import kotlin.math.pow
import kotlin.math.round

data class WeightRange(
    val minWeight: Double,
    val maxWeight: Double
)

object BmiCalculator {
    private const val MIN_NORMAL_BMI = 18.5
    private const val MIN_OVERWEIGHT_BMI = 25.0
    private const val OBESE_BMI = 30.0

    fun calculateBmi(weightKg: Double, heightCm: Double): Double {
        require(weightKg > 0.0) { "Вес должен быть положительным числом" }
        require(heightCm > 0.0) { "Рост должен быть положительным числом" }

        val heightM = heightCm / 100.0
        val bmi = weightKg / heightM.pow(2)

        return round(bmi * 10.0) / 10.0
    }

    fun classifyBmi(bmi: Double): BmiCategory {
        require(bmi > 0.0) { "ИМТ должен быть положительным числом" }

        return when {
            bmi < MIN_NORMAL_BMI -> BmiCategory.UNDERWEIGHT
            bmi < MIN_OVERWEIGHT_BMI -> BmiCategory.NORMAL
            bmi < OBESE_BMI -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }
    }

    fun getHealthyWeightRange(heightCm: Double): WeightRange {
        require(heightCm > 0.0) { "Рост должен быть положительным числом" }

        val heightM = heightCm / 100.0
        val heightSquared = heightM.pow(2)
        val min = round(MIN_NORMAL_BMI * heightSquared * 10.0) / 10.0
        val max = round(MIN_OVERWEIGHT_BMI * heightSquared * 10.0) / 10.0

        return WeightRange(minWeight = min, maxWeight = max)
    }

    fun calculateWeightDelta(currentWeightKg: Double, heightCm: Double): Double {
        require(currentWeightKg > 0.0) { "Вес должен быть положительным числом" }
        val range = getHealthyWeightRange(heightCm)

        return when {
            currentWeightKg < range.minWeight -> round((currentWeightKg - range.minWeight) * 10.0) / 10.0
            currentWeightKg > range.maxWeight -> round((range.maxWeight - currentWeightKg) * 10.0) / 10.0
            else -> 0.0
        }
    }

    fun calculatePonderalIndex(weightKg: Double, heightCm: Double): Double {
        require(weightKg > 0.0) { "Вес должен быть положительным числом" }
        require(heightCm > 0.0) { "Рост должен быть положительным числом" }
        val heightM = heightCm / 100.0
        val pi = weightKg / heightM.pow(3)
        return round(pi * 10.0) / 10.0
    }
}