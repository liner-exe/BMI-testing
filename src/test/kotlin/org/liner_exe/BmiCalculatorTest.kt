package org.liner_exe

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BmiCalculatorTest {

    @Test
    @DisplayName("calculateBmi: Корректный расчёт базового значения")
    fun `calculateBmi should compute correct value`() {
        val result = BmiCalculator.calculateBmi(70.0, 175.0)
        assertEquals(22.9, result, 0.01)
    }

    @Test
    @DisplayName("calculateBmi: Выброс исключения при некорректных аргументах")
    fun `calculateBmi should throw exception on non-positive input`() {
        assertThrows<IllegalArgumentException> { BmiCalculator.calculateBmi(-10.0, 180.0) }
        assertThrows<IllegalArgumentException> { BmiCalculator.calculateBmi(70.0, 0.0) }
    }

    @ParameterizedTest(name = "ИМТ {0} -> Категория {1}")
    @CsvSource(
        "18.4, UNDERWEIGHT",
        "18.5, NORMAL",
        "24.9, NORMAL",
        "25.0, OVERWEIGHT",
        "29.9, OVERWEIGHT",
        "30.0, OBESE",
        "35.5, OBESE"
    )
    @DisplayName("classifyBmi: Проверка классов эквивалентности и граничных значений")
    fun `classifyBmi should map to expected category`(bmiValue: Double, expectedCategory: BmiCategory) {
        val actual = BmiCalculator.classifyBmi(bmiValue)
        assertEquals(expectedCategory, actual)
    }

    @Test
    @DisplayName("getHealthyWeightRange: Расчёт границ для роста 180 см")
    fun `getHealthyWeightRange should return correct bounds`() {
        val range = BmiCalculator.getHealthyWeightRange(180.0)
        assertEquals(59.9, range.minWeight, 0.1)
        assertEquals(81.0, range.maxWeight, 0.1)
    }

    @Test
    @DisplayName("calculateWeightDelta: Вес в пределах нормы")
    fun `calculateWeightDelta should return zero when weight is in range`() {
        val delta = BmiCalculator.calculateWeightDelta(70.0, 180.0)
        assertEquals(0.0, delta, 0.01)
    }

    @Test
    @DisplayName("calculateWeightDelta: Избыточный вес (требуется сброс)")
    fun `calculateWeightDelta should return negative delta when overweight`() {
        val delta = BmiCalculator.calculateWeightDelta(90.0, 180.0)
        assertEquals(-9.0, delta, 0.1)
    }

    @Test
    @DisplayName("calculateWeightDelta: Дефицит веса (требуется набор)")
    fun `calculateWeightDelta should return positive delta when underweight`() {
        val delta = BmiCalculator.calculateWeightDelta(50.0, 180.0)
        assertEquals(9.9, delta, 0.1)
    }

    @Test
    @DisplayName("calculatePonderalIndex: Корректный расчёт индекса Рорера")
    fun `calculatePonderalIndex should return expected value`() {
        val pi = BmiCalculator.calculatePonderalIndex(70.0, 175.0)
        assertEquals(13.1, pi, 0.1)
    }
}